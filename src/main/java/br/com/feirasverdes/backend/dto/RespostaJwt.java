package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

public class RespostaJwt implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String token;

	private final Long idUsuario;

	public RespostaJwt(final String token, final Long idUsuario) {
		this.token = token;
		this.idUsuario = idUsuario;
	}

	public String getToken() {
		return this.token;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

}
