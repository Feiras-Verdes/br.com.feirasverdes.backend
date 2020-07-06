package br.com.feirasverdes.backend.exception;

public class FeiraNaoPertenceAoUsuarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public FeiraNaoPertenceAoUsuarioException(final String mensagem) {
		super(mensagem);
	}

	public FeiraNaoPertenceAoUsuarioException() {
		super();
	}

}
