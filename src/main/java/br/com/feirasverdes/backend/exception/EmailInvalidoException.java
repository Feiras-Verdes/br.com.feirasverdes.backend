package br.com.feirasverdes.backend.exception;

public class EmailInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailInvalidoException(final String mensagem) {
		super(mensagem);
	}

	public EmailInvalidoException() {
		super();
	}

}
