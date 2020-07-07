package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

public class NovaSenhaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
