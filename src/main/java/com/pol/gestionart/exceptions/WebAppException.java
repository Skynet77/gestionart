package com.pol.gestionart.exceptions;

public class WebAppException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorData errorBody;

	public WebAppException(String message) {
		super(message);
	}

	public WebAppException(ErrorData errorBody) {
		this.errorBody = errorBody;
	}

	public ErrorData getErrorBody() {
		return errorBody;
	}

	public void setErrorBody(ErrorData errorBody) {
		this.errorBody = errorBody;
	}
}
