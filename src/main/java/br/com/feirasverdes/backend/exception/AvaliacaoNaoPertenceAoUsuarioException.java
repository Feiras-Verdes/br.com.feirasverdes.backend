package br.com.feirasverdes.backend.exception;

public class AvaliacaoNaoPertenceAoUsuarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public AvaliacaoNaoPertenceAoUsuarioException(final String mensagem) {
		super(mensagem);
	}

	public AvaliacaoNaoPertenceAoUsuarioException() {
		super();
	}

}
