package com.yorbit.docker.exception;

public class InvalidException extends Exception {

	String str1;

	public InvalidException(String str2) {
		str1 = str2;
	}

	public String toString() {
		return (str1);
	}

}
