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

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.entidade.Avaliacao;

@RestController
@CrossOrigin
@RequestMapping(value = "/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Avaliacao> salvarAvaliacao(@RequestBody Avaliacao avaliacao) {
		Avaliacao avaliacaoSalvo = new Avaliacao();
		try {
			avaliacaoSalvo = dao.save(avaliacao);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(avaliacaoSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(avaliacaoSalvo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public Response atualizarAvaliacao(@PathVariable(value = "id", required = true) Long id,
			@RequestBody Avaliacao avaliacao) {
		avaliacao.setId(id);
		dao.save(avaliacao);
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
	public ResponseEntity<Avaliacao> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Avaliacao avaliacao = dao.getOne(id);
		return ResponseEntity.ok(avaliacao);
	}
	
}
