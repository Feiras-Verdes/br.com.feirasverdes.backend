package br.com.feirasverdes.backend.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Usuario;

@RestController
@CrossOrigin
@RequestMapping(value = "/feira")
public class FeiraController {

	@Autowired
	private FeiraDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Feira> salvarFeira(@RequestBody Feira feira) {
		Feira feiraSalva = new Feira();
		try {
			feiraSalva = dao.save(feira);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(feiraSalva, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(feiraSalva, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public Response atualizarFeira(@PathVariable(value = "id", required = true) Long id, @RequestBody Feira feira) {
		feira.setId(id);
		dao.save(feira);
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

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome/{nome}")
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome") String nome) {
		List<Feira> feiras = dao.pesquisarPorNome(nome);
		return ResponseEntity.ok(feiras);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Feira> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Feira feira = dao.getOne(id);
		return ResponseEntity.ok(feira);
	}
}
