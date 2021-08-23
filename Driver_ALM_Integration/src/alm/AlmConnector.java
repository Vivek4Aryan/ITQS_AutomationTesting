package alm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AlmConnector {

	private AlmRestConnector con;

	public AlmConnector(final String serverUrl, final String domain, final String project) {
		this.con = AlmRestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);
	}

	public AlmConnector() {
		this.con = AlmRestConnector.getInstance();
	}

	/**
	 * Returns the current authentication status.If the authenticationPoint is null,
	 * the user is already authenticated. In this case no login necessary.
	 * 
	 * @param username
	 * @param password
	 * @return boolean
	 * @throws IOException
	 * @throws Exception 
	 */
	public boolean login(String username, String password) throws Exception {

		String authenticationPoint = this.authenticationStatus();
		if (authenticationPoint != null) {
			return this.loginWithAuthentication(authenticationPoint, username, password);
		}
		return true;
	}

	/**
	 * create a string that looks like: "Basic ((username:password)<as
	 * bytes>)<64encoded>"
	 * 
	 * @param loginUrl
	 * @param username
	 * @param password
	 * @return boolean
	 * @throws Exception 
	 */
	private boolean loginWithAuthentication(String loginUrl, String username, String password) throws Exception {

		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64.getEncoder().encodeToString(credBytes);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", credEncodedString);
		Response response = con.httpGet(loginUrl, null, map);
		boolean status = response.getStatusCode() == HttpURLConnection.HTTP_OK;
		return status;
	}

	/**
	 * Note the get operation logout by setting authentication cookies to:
	 * LWSSO_COOKIE_KEY="" via server response header Set-Cookie
	 * 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean logout() throws Exception {

		Response response = con.httpGet(con.buildUrl("authentication-point/logout"), null, null);
		return (response.getStatusCode() == HttpURLConnection.HTTP_OK);
	}

	/**
	 * If a user is already authenticated, the return value is set to null and the
	 * current connection is kept open.If a user is not authenticated yet, return an
	 * URL at which he can authenticate himself via www-authenticate.
	 * 
	 * @return string
	 * @throws Exception 
	 */
	public String authenticationStatus() throws Exception {

		String isAuthenticateUrl = con.buildUrl("rest/is-authenticated");
		String status = "";
		Response response = con.httpGet(isAuthenticateUrl, null, null);
		int responseCode = response.getStatusCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			status = null;
		} else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
			status = con.buildUrl("authentication-point/authenticate");
		}
		return status;
	}
}
