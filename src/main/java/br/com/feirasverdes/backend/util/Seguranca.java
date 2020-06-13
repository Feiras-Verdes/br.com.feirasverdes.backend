package br.com.feirasverdes.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class Seguranca {

	@Autowired
	private AuthenticationManager auhenticationManager;

	private void autenticar(String email, String senha) {
		auhenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
	}

}
