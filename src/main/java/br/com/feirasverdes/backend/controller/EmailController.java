package br.com.feirasverdes.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dto.RecuperarSenhaDTO;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.service.UsuarioService;

@RestController
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsuarioService service;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@RequestMapping(path = "/nova-senha", method = RequestMethod.POST)
	public ResponseEntity<?> sendMail(@Valid @RequestBody RecuperarSenhaDTO recuperarSenhaDTO) {
		try {
			mailSender.send(service.gerarSenha(recuperarSenhaDTO));
			return ResponseEntity.ok("A nova senha foi enviada para o e-mail informado");
		} catch (EmailInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespostaDto(e.getMessage()));
		}
	}
}