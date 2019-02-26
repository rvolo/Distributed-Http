package com.dhttp.storage.model;

import com.dhttp.data.model.RequestType;
import com.dhttp.data.model.HttpRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Model for storing a {@link HttpRecord} in the database
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class HttpEntity extends HttpRecord {
	@NotEmpty
	@Column(columnDefinition = "LONGTEXT")
	private String source;

	@Id
	@GenericGenerator(name = "assigned-sequence", strategy = "com.dhttp.storage.config.Base62IdGenerator")
	@GeneratedValue(generator = "assigned-sequence", strategy = GenerationType.SEQUENCE)
	@Override
	public String getId() {
		return super.getId();
	}

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@Override
	public Date getCreatedOn() {
		return super.getCreatedOn();
	}

	@NotEmpty
	@Override
	public String getUrl() {
		return super.getUrl();
	}

	@NotNull
	@Enumerated(value = EnumType.STRING)
	@Override
	public RequestType getType() {
		return super.getType();
	}

	@JsonIgnore
	@NotEmpty
	@Column(columnDefinition = "LONGTEXT")
	public String getSource() {
		return source;
	}

	public HttpEntity setSource(String source) {
		this.source = source;
		return this;
	}
}
