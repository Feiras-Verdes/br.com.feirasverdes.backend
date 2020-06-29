package br.com.feirasverdes.backend.exception;

public class UsuarioNaoEOrganizadorException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEOrganizadorException(final String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEOrganizadorException() {
		super();
	}

}