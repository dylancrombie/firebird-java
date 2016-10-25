package com.oasis.firebird.communication;

import java.util.ArrayList;
import java.util.List;

import com.oasis.firebird.core.ErrorMessage;

@Deprecated
public class ClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072038429346217260L;

	private List<ErrorMessage> errorMessages;

	public ClientException() {
		super();
		errorMessages = new ArrayList<>();
	}

	public ClientException(ErrorMessage errorMessage) {
		this();
		this.errorMessages.add(errorMessage);
	}

	public ClientException(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public void addErrorMessage(ErrorMessage errorMessage) {
		this.errorMessages.add(errorMessage);
	}

}
