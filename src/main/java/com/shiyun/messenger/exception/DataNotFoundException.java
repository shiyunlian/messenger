package com.shiyun.messenger.exception;

public class DataNotFoundException extends RuntimeException{

	// runtime exception has a serial id
	private static final long serialVersionUID = 2516941486207395855L;
	
	public DataNotFoundException(String message) {
		super(message);
	}

}
