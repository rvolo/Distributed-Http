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
	ErrorSavingToStorageException(Exception ex) {
		super(ex);
	}

	/**
	 * http error code received from storage server
	 *
	 * @param statusCode http status code
	 */
	ErrorSavingToStorageException(int statusCode) {
		super("Received " + statusCode + " from storage server");
	}
}
