package br.com.feirasverdes.backend.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.RecuperarSenhaDTO;
import br.com.feirasverdes.backend.entidade.Usuario;

@RestController
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UsuarioDao usuarioDao; 

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @RequestMapping(path = "/nova-senha", method = RequestMethod.POST)
    public String sendMail(@Valid @RequestBody RecuperarSenhaDTO recuperarSenhaDTO) {
    	
    	Usuario usuario = usuarioDao.pesquisarPorEmail(recuperarSenhaDTO.getEmail());
    	if (usuario != null) {
    		String senha = UUID.randomUUID().toString().replace("-", "");
    		usuario.setSenha(passwordEncoder.encode(senha));
    		usuarioDao.save(usuario);
    		
    		SimpleMailMessage message = new SimpleMailMessage();
	        message.setSubject("Feiras verdes - Nova senha");
	        message.setText("Segue abaixo a nova senha para acessar o sistema Feiras Verdes:\n " + senha);
	        message.setTo(usuario.getEmail());
	        message.setFrom("feirasverdes@gmail.com");
	        try {
	        	mailSender.send(message);
	        	return "A nova senha foi enviada para o e-mail informado";
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return "Erro ao enviar email.";
	        }
    	}
    	
    	return "A nova senha foi enviada para o e-mail informado";
       
    }
}