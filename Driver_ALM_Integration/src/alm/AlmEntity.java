package alm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class AlmEntity {

	private AlmRestConnector conn;

	public AlmEntity() {
		conn = AlmRestConnector.getInstance();
	}

	/**
	 * Post new entity on alm rest api and returns response in string
	 * 
	 * @param collectionUrl
	 * @param postedEntityXml
	 * @return string
	 * @throws Exception
	 */
	public String createEntity(String collectionUrl, String postedEntityXml) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		Response postResponse = conn.httpPost(collectionUrl, postedEntityXml.getBytes(), requestHeaders);
		Exception failure = postResponse.getFailure();
		if (failure != null) {
			throw failure;
		}
		String entityUrl = postResponse.getResponseHeaders().get("Location").iterator().next();
		return entityUrl;
	}

	/**
	 * Delete created entity on alm rest api and returns response in string
	 * 
	 * @param entityUrl
	 * @return string
	 * @throws Exception 
	 */
	public String deleteEntity(String entityUrl) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");
		Response serverResponse = conn.httpDelete(entityUrl, requestHeaders);
		if (serverResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException(serverResponse.toString());
		}
		return serverResponse.toString();
	}

	/**
	 * Update created entity on alm rest api and returns response
	 * 
	 * @param entityUrl
	 * @param updatedEntityXml
	 * @return http response
	 * @throws Exception 
	 */
	public Response updateEntity(String entityUrl, String updatedEntityXml) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		Response putResponse = conn.httpPut(entityUrl, updatedEntityXml.getBytes(), requestHeaders);
		if (putResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException(putResponse.toString());
		}
		return putResponse;
	}

	/**
	 * Retrieve response for entitly request url from alm
	 * 
	 * @param entityUrl
	 * @param query
	 * @return http response
	 * @throws Exception 
	 */
	public Response readEntity(String entityUrl, String query) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/json");
		Response getResponse;
		getResponse = conn.httpGet(entityUrl, query, requestHeaders);
		if (getResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException(getResponse.toString());
		}
		return getResponse;
	}

	/**
	 * Attach execution report with respective alm test run
	 * 
	 * @param entityUrl
	 * @param fileData
	 * @param filename
	 * @return string
	 * @throws Exception 
	 */
	public String attachExecutionWordDocReport(String entityUrl, byte[] fileData, String filename) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Slug", filename);
		requestHeaders.put("Content-Type", "application/octet-stream");
		Response response = conn.httpPost(entityUrl + "/attachments", fileData, requestHeaders);
		if (response.getStatusCode() != HttpURLConnection.HTTP_CREATED) {
			throw new IOException(response.toString());
		}
		return response.getResponseHeaders().get("Location").iterator().next();
	}
	
	
	public void downloadAttachment(String entityUrl, String query) throws Exception {

		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/octet-stream");
		requestHeaders.put("Accept", "application/octet-stream");
		System.out.println(entityUrl);

		Response getResponse = conn.httpGet(entityUrl, query, requestHeaders);

		if (getResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception(getResponse.toString());
		}

	}
	
	public byte[] readAttachmentData(String attachmentUrl) throws Exception {

        Map<String, String> requestHeaders = new HashMap<String, String>();

        /* A get operation that specifies via accept header that
           we must have an application/octet-stream reply.
           An alt query parameter could also have been used. */
        requestHeaders.put("Accept", "application/octet-stream");

        Response readResponse =
                conn.httpGet(attachmentUrl, "alt=application/octet-stream", requestHeaders);

        if (readResponse.getStatusCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception(readResponse.toString());
        }

        return readResponse.getResponseData();
    }
}
