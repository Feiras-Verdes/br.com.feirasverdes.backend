package br.com.feirasverdes.backend.exception;

public class AutenticacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public AutenticacaoException(final String mensagem) {
		super(mensagem);
	}

	public AutenticacaoException() {
		super();
	}

}