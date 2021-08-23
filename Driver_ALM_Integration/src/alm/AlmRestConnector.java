package alm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;

/**
 * This class keeps the state of the connection for the examples. This class is
 * a thus sharing state singleton. All examples get the instance in their
 * default constructors - (cookies, server url).
 *
 * Some simple methods are implemented to get commonly used paths.
 */

public class AlmRestConnector {

	protected Map<String, String> cookies;
	protected String serverUrl;
	protected String domain;
	protected String project;

	public AlmRestConnector init(Map<String, String> cookies, String serverUrl, String domain, String project) {

		this.cookies = cookies;
		this.serverUrl = serverUrl;
		this.domain = domain;
		this.project = project;
		return this;
	}

	private AlmRestConnector() {
	}

	private static AlmRestConnector instance = new AlmRestConnector();

	public static AlmRestConnector getInstance() {
		return instance;
	}

	/**
	 * To build a url for entity collection: test-set, test-instance, test etc.
	 * 
	 * @param entityType
	 * @return string
	 * @throws URISyntaxException
	 * @throws MalformedURLException
	 */
	public String buildEntityCollectionUrl(String entityType) throws MalformedURLException, URISyntaxException {
		return buildUrl(String.format("rest/domains/%1$s/projects/%2$s/%3$s%4$s", domain, project, entityType, "s"));
	}

	/**
	 * To build a url for http request
	 * 
	 * @param path
	 * @return string
	 * @throws URISyntaxException
	 * @throws MalformedURLException
	 */
	public String buildUrl(String path) throws MalformedURLException, URISyntaxException {

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https");
		builder.setHost(serverUrl);
		builder.setPath(path);
		return builder.build().toURL().toString();
	}

	/**
	 * @return cookies
	 */
	public Map<String, String> getCookies() {
		return cookies;
	}

	/**
	 * Set the cookies
	 * 
	 * @param cookies
	 */
	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Returns response for http PUT request
	 * 
	 * @param url
	 * @param data
	 * @param headers
	 * @return http response
	 * @throws IOException
	 */
	public Response httpPut(String url, byte[] data, Map<String,
	           String> headers) throws Exception {

	        return doHttp("PUT", url, null, data, headers, cookies);
	    }

	/**
	 * Returns response for http POST request
	 * 
	 * @param url
	 * @param data
	 * @param headers
	 * @return http response
	 * @throws IOException
	 */
	 public Response httpPost(String url, byte[] data, Map<String,
	           String> headers) throws Exception {

	        return doHttp("POST", url, null, data, headers, cookies);
	    }

	/**
	 * Returns response for http DELETE request
	 * 
	 * @param url
	 * @param data
	 * @param headers
	 * @return http response
	 * @throws IOException
	 */
	public Response httpDelete(String url, Map<String, String> headers)
	           throws Exception {

	        return doHttp("DELETE", url, null, null, headers, cookies);
	    }

	/**
	 * Returns response for http GET request
	 * 
	 * @param url
	 * @param data
	 * @param headers
	 * @return http response
	 * @throws IOException
	 */
	 public Response httpGet(String url, String queryString, Map<String,
	           String> headers)throws Exception {

	        return doHttp("GET", url, queryString, null, headers, cookies);
	    }

	/**
	 * Return response http for operations: get post put delete
	 * 
	 * @param type
	 * @param url
	 * @param queryString
	 * @param data
	 * @param headers
	 * @param cookies
	 * @return http response
	 * @throws IOException
	 */
	
	private Response doHttp(
            String type,
            String url,
            String queryString,
            byte[] data,
            Map<String, String> headers,
            Map<String, String> cookies) throws Exception {

		if ((queryString != null) && !queryString.isEmpty()) {
			url += "?" + queryString + "&page-size=max";
		} else {
			url += "?pagesize=max";
		}

        HttpURLConnection con =
            (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod(type);
        String cookieString = getCookieString();

        prepareHttpRequest(con, headers, data, cookieString);
        con.connect();
        Response ret = retrieveHtmlResponse(con);

        updateCookies(ret);

        return ret;
    }

	/**
	 * To prepare http request
	 * 
	 * @param con
	 * @param headers
	 * @param bytes
	 * @param cookieString
	 * @throws IOException
	 */
	private void prepareHttpRequest(HttpURLConnection con, Map<String, String> headers, byte[] bytes,
			String cookieString) throws IOException {

		String contentType = null;
		if ((cookieString != null) && !cookieString.isEmpty()) {
			con.setRequestProperty("Cookie", cookieString);
		}
		if (headers != null) {
			contentType = headers.remove("Content-Type");
			Iterator<Entry<String, String>> headersIterator = headers.entrySet().iterator();
			while (headersIterator.hasNext()) {
				Entry<String, String> header = headersIterator.next();
				con.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		if ((bytes != null) && (bytes.length > 0)) {
			con.setDoOutput(true);
			if (contentType != null) {
				con.setRequestProperty("Content-Type", contentType);
			}
			OutputStream out = con.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
		}
	}

	/**
	 * To return a response from the server to the previously submitted http request
	 * 
	 * @param con
	 * @return response
	 * @throws IOException
	 */
	private Response retrieveHtmlResponse(HttpURLConnection con)
            throws Exception {

        Response ret = new Response();

        ret.setStatusCode(con.getResponseCode());
        ret.setResponseHeaders(con.getHeaderFields());

        InputStream inputStream;
        //select the source of the input bytes, first try 'regular' input
        try {
            inputStream = con.getInputStream();
        }

        /*
         If the connection to the server somehow failed, for example 404 or 500,
         con.getInputStream() will throw an exception, which we'll keep.
         We'll also store the body of the exception page, in the response data.
         */
        catch (Exception e) {

            inputStream = con.getErrorStream();
            ret.setFailure(e);
        }

        // This actually takes the data from the previously set stream
        // (error or input) and stores it in a byte[] inside the response
        ByteArrayOutputStream container = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int read;
        while ((read = inputStream.read(buf, 0, 1024)) > 0) {
            container.write(buf, 0, read);
        }

        ret.setResponseData(container.toByteArray());

        return ret;
    }

	/**
	 * To update cookies
	 * 
	 * @param response
	 */
	public void updateCookies(Response response) {

		Iterable<String> newCookies = response.getResponseHeaders().get("Set-Cookie");
		if (newCookies != null) {
			for (String cookie : newCookies) {
				int equalIndex = cookie.indexOf('=');
				int semicolonIndex = cookie.indexOf(';');
				String cookieKey = cookie.substring(0, equalIndex);
				String cookieValue = cookie.substring(equalIndex + 1, semicolonIndex);
				cookies.put(cookieKey, cookieValue);
			}
		}
	}

	/**
	 * To get cookies string
	 * 
	 * @return string
	 */
	public String getCookieString() {

		StringBuilder sb = new StringBuilder();
		if (!cookies.isEmpty()) {
			Set<Entry<String, String>> cookieEntries = cookies.entrySet();
			for (Entry<String, String> entry : cookieEntries) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
			}
		}
		String ret = sb.toString();
		return ret;
	}
}
