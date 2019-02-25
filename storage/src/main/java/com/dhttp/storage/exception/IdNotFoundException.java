package com.dhttp.storage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a requested id cannot be found in the database
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdNotFoundException extends StorageException {
	public static IdNotFoundException httpEntityIdNotFound(String id) {
		return new IdNotFoundException(id + " not found on server");
	}

	private IdNotFoundException(String message) {
		super(message);
	}
}
