package com.example.demo.exceptions;

/**
 * 
 * @author Hekate
 * @see https://blog.csdn.net/qq_34021712/article/details/80791219
 *
 */
public class PrincipalIdNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Principal Id shouldn't be null!";

	public PrincipalIdNullException(Class<?> clazz, String idMethodName) {
		super(clazz + " id field: " + idMethodName + ", value is null\n" + MESSAGE);
	}
}
