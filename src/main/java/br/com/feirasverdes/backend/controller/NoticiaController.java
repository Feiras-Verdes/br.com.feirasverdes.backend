package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.service.NoticiaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/noticias")
public class NoticiaController {

	@Autowired
	NoticiaService service;

	@Autowired
	private NoticiaDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Noticia> salvarNoticia(@Valid @RequestBody Noticia noticia) {
		Noticia noticiaSalvo = new Noticia();
		try {
			noticiaSalvo = dao.save(noticia);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(noticiaSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(noticiaSalvo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public ResponseEntity<?> atualizarNoticia(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute NoticiaDto noticia) {
		try {
			service.atualizarNoticia(id, noticia);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/excluir")
	public Response excluir(@PathVariable(value = "id", required = true) Long id) {
		dao.deleteById(id);
		return Response.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarTodos")
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(dao.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Noticia> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Noticia noticia = dao.getOne(id);
		return ResponseEntity.ok(noticia);
	}

}
