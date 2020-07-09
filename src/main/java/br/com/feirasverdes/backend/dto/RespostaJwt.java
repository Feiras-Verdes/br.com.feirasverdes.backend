package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

public class RespostaJwt implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String token;

	public RespostaJwt(final String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

}
