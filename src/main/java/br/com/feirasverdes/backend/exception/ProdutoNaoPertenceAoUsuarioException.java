package br.com.feirasverdes.backend.exception;

public class ProdutoNaoPertenceAoUsuarioException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoPertenceAoUsuarioException(final String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoPertenceAoUsuarioException() {
		super();
	}

}
