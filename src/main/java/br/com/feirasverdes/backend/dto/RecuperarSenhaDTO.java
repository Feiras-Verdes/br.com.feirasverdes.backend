package br.com.feirasverdes.backend.dto;

import javax.validation.constraints.NotBlank;

public class RecuperarSenhaDTO {
	
	@NotBlank
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
