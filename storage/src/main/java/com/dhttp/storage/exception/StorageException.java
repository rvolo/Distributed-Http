package com.dhttp.storage.exception;

/**
 * Base exception class for all exceptions created by the server
 */
@SuppressWarnings("unused")
abstract class StorageException extends Exception {
	StorageException(String message) {
		super(message);
	}

	StorageException(String message, Throwable cause) {
		super(message, cause);
	}

	StorageException(Throwable cause) {
		super(cause);
	}
}
