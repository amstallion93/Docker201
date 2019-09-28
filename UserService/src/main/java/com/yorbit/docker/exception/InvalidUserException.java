package com.yorbit.docker.exception;

@SuppressWarnings("serial")
public class InvalidUserException extends Exception {

	public InvalidUserException(String string) {
		super(string);
	}
}
