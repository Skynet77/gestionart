package com.pol.gestionart.exceptions;

public class AjaxException extends WebAppException {
	private static final long serialVersionUID = 1L;

	public AjaxException(String message) {
		super(message);
	}

	public AjaxException(ErrorData errorBody) {
		super(errorBody);
	}

}
