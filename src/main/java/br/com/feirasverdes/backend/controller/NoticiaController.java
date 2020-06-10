package br.com.feirasverdes.backend.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.entidade.Noticia;


@RestController
@CrossOrigin
@RequestMapping(value = "/noticia")
public class NoticiaController {


	@Autowired
	private NoticiaDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Noticia> salvarNoticia(@RequestBody Noticia noticia) {
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
	public Response atualizarNoticia(@PathVariable(value = "id", required = true) Long id,
			@RequestBody Noticia noticia) {
		noticia.setId(id);
		dao.save(noticia);
		return Response.ok().build();
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
