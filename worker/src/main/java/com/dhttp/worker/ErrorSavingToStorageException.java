package com.dhttp.worker;

/**
 * Wrapper for error saving to storage microservice
 */
class ErrorSavingToStorageException extends RuntimeException {
	/**
	 * Caught exception executing request to storage server
	 *
	 * @param ex exception
	 */
	static ErrorSavingToStorageException caughtException(Exception ex) {
		return new ErrorSavingToStorageException(ex);
	}

	/**
	 * http error code received from storage server
	 *
	 * @param statusCode http status code
	 */
	static ErrorSavingToStorageException errorCodeNot200(int statusCode) {
		return new ErrorSavingToStorageException("Received " + statusCode + " from storage server");
	}

	/**
	 * Object sent by storage server is missing required fields
	 */
	static ErrorSavingToStorageException invalidReturnObject() {
		return new ErrorSavingToStorageException("Storage server returned incorrect object");
	}

	private ErrorSavingToStorageException(String msg) {
		super(msg);
	}

	private ErrorSavingToStorageException(Throwable cause) {
		super(cause);
	}
}
