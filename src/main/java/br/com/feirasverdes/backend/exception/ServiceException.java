package br.com.feirasverdes.backend.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceException(final String message) {
		super(message);
	}

	public ServiceException() {
		super();
	}

}
