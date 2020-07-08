package br.com.feirasverdes.backend.exception;

public class EstandeNaoPertenceAoUsuarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public EstandeNaoPertenceAoUsuarioException(final String mensagem) {
		super(mensagem);
	}

	public EstandeNaoPertenceAoUsuarioException() {
		super();
	}

}
