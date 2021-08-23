package alm;

import java.util.Map;

public class Response {

	private Map<String, ? extends Iterable<String>> responseHeaders = null;
	private byte[] responseData = null;
	private Exception failure = null;
	private int statusCode = 0;

	public Response(Map<String, Iterable<String>> responseHeaders, byte[] responseData, Exception failure,int statusCode) {
		super();
		this.responseHeaders = responseHeaders;
		this.responseData = responseData;
		this.failure = failure;
		this.statusCode = statusCode;
	}

	public Response() {
	}

	/**
	 * To get response headers
	 * 
	 * @return Map<String, ? extends Iterable<String>>
	 */
	public Map<String, ? extends Iterable<String>> getResponseHeaders() {
		return responseHeaders;
	}

	/**
	 * To set response headers
	 * 
	 * @param responseHeaders
	 */
	public void setResponseHeaders(Map<String, ? extends Iterable<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	/**
	 * To get response data
	 * 
	 * @return byte[]
	 */
	public byte[] getResponseData() {
		return responseData;
	}

	/**
	 * To set response data
	 * 
	 * @param responseData
	 */
	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}

	/**
	 * To get failure
	 * 
	 * @return Exception
	 */
	public Exception getFailure() {
		return failure;
	}

	/**
	 * To set failure
	 * 
	 * @param failure
	 */
	public void setFailure(Exception failure) {
		this.failure = failure;
	}

	/**
	 * To get status code
	 * 
	 * @return int
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * To set status code
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return new String(this.responseData);
	}
}
