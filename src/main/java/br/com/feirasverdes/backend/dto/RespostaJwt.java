package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

public class RespostaJwt implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String jwttoken;

	public RespostaJwt(final String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return jwttoken;
	}

}
