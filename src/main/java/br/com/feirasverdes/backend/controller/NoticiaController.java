package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.exception.NoticiaNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.service.NoticiaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/noticias")
public class NoticiaController {

	@Autowired
	NoticiaService service;

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Noticia> salvarNoticia(@Valid @ModelAttribute NoticiaDto noticia) {
		Noticia noticiaSalvo = new Noticia();
		try {
			noticia.setDataPublicacao(LocalDateTime.now());
			noticiaSalvo = service.salvar(noticia);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(noticiaSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(noticiaSalvo, HttpStatus.OK);
	}

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<?> atualizarNoticia(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute NoticiaDto noticia) throws IOException {
		try {
			service.atualizarNoticia(id, noticia);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (NoticiaNaoPertenceAoUsuarioException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
	}

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirNoticia(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoticiaNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<Noticia> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Noticia noticia = service.pesquisarPorId(id);
		return ResponseEntity.ok(noticia);
	}

}
