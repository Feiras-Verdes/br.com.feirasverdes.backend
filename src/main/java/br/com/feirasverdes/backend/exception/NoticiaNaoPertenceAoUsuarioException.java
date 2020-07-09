package br.com.feirasverdes.backend.exception;

public class NoticiaNaoPertenceAoUsuarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoticiaNaoPertenceAoUsuarioException(final String mensagem) {
		super(mensagem);
	}

	public NoticiaNaoPertenceAoUsuarioException() {
		super();
	}

}
