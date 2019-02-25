package com.dhttp.storage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Model for storing a http object in the database
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class HttpEntity {
	@Id
	@GenericGenerator(name = "assigned-sequence", strategy = "com.dhttp.storage.config.Base62IdGenerator")
	@GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
	private String id;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdOn;

	@NotEmpty
	@Column(columnDefinition = "LONGTEXT")
	private String source;

	@NotEmpty
	private String url;

	@NotEmpty
	private String type;

	public String getId() {
		return id;
	}

	public HttpEntity setId(String id) {
		this.id = id;
		return this;
	}

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, timezone = "UTC")
	public Date getCreatedOn() {
		return createdOn;
	}

	public HttpEntity setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	/**
	 * @return http source
	 */
	@JsonIgnore
	public String getSource() {
		return source;
	}

	public HttpEntity setSource(String source) {
		this.source = source;
		return this;
	}

	/**
	 * @return requested url
	 */
	public String getUrl() {
		return url;
	}

	public HttpEntity setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * @return http type (GET, POST, PUT, DELETE)
	 */
	public String getType() {
		return type;
	}

	public HttpEntity setType(String type) {
		this.type = type;
		return this;
	}
}
