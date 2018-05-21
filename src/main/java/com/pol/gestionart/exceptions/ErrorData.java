package com.pol.gestionart.exceptions;

import java.io.Serializable;

public class ErrorData implements Serializable{
	private static final long serialVersionUID = 6255800385105796703L;
	private String type;
	private String code;
	private String description;
	private String userMessage;
	
	public ErrorData() {
		
	}
	public ErrorData(String type, String code, String description,
			String userMessage) {
		this.type = type;
		this.code = code;
		this.description = description;
		this.userMessage = userMessage;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ErrorData [type=" + type + ", code=" + code + ", description="
				+ description + ", userMessage=" + userMessage + "]";
	}
	
	
}
