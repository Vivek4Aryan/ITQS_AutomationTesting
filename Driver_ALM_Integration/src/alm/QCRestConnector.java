package alm;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class QCRestConnector {

	private static QCRestConnector instance;

	public static QCRestConnector getInstance() {
		if (instance == null) {
			instance = new QCRestConnector();
		}
		return instance;
	}

	/**
	 * To get Quality Center session
	 * @throws Exception 
	 */
	public void getQCSession() throws Exception {

		String qcsessionurl = AlmRestConnector.getInstance().buildUrl("rest/site-session");
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		Response resp = AlmRestConnector.getInstance().httpPost(qcsessionurl, null, requestHeaders);
		AlmRestConnector.getInstance().updateCookies(resp);
	}
}
