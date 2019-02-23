package com.dhttp.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response xml object to be returned by all requests
 * Generic object that can bve a error message or a successful response question
 */
@XmlRootElement
public class RequestWrapper {
	private final boolean success;
	private final String error;
	private final String url;
	private final RequestType type;
	private final int responseCode;
	private final String source;

	public static RequestWrapper createNew(RequestType type, String url, Exception ex) {
		return new RequestWrapper(false, url, type, ex.getMessage(), -1, null);
	}

	public static RequestWrapper createNew(RequestType type, String url, int responseCode, String source) {
		return new RequestWrapper(true, url, type, null, responseCode, source);
	}

	private RequestWrapper(boolean success, String url, RequestType type, String error, int responseCode, String source) {
		this.success = success;
		this.error = error;
		this.url = url;
		this.type = type;
		this.responseCode = responseCode;
		this.source = source;
	}

	/**
	 * @return http response code, -1 for error
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @return raw html wrapped as a CDATA
	 */
	@JacksonXmlCData
	public String getSource() {
		return source;
	}

	/**
	 * @return if execution was successful
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return human readable exception message
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return requested url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return http request type
	 */
	public RequestType getType() {
		return type;
	}
}
