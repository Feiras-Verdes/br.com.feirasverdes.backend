package br.com.feirasverdes.backend.exception;

public class TipoInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public TipoInvalidoException(final String mensagem) {
		super(mensagem);
	}

	public TipoInvalidoException() {
		super();
	}

}
