package com.dhttp.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Json object to be passed between microservices and used as public response
 */

public class HttpRecord {
	private String id;
	private Date createdOn;
	private String url;
	private RequestType type;

	public String getId() {
		return id;
	}

	public HttpRecord setId(String id) {
		this.id = id;
		return this;
	}

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, timezone = "UTC")
	public Date getCreatedOn() {
		return createdOn;
	}

	public HttpRecord setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	/**
	 * @return requested url
	 */
	public String getUrl() {
		return url;
	}

	public HttpRecord setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * @return http type (GET, POST, PUT, DELETE)
	 */
	public RequestType getType() {
		return type;
	}

	public HttpRecord setType(RequestType type) {
		this.type = type;
		return this;
	}

	@Override
	public String toString() {
		return "HttpRecord{" +
				"id='" + id + '\'' +
				", createdOn=" + createdOn +
				", url='" + url + '\'' +
				", type=" + type +
				'}';
	}

	/**
	 * Check to see if the object has populated values
	 *
	 * @return if required values are not null
	 */
	public boolean isValid() {
		if (id == null || id.isEmpty()) return false;
		if (createdOn == null) return false;
		if (url == null || url.isEmpty()) return false;
		if (type == null) return false;
		return true;
	}
}
