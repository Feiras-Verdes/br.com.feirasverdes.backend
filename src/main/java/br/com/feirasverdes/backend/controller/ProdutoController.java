package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.AtualizarEstandeDto;
import br.com.feirasverdes.backend.dto.AtualizarProdutoDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.service.ProdutoService;

@RestController
@CrossOrigin
@RequestMapping(value = "/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@Autowired
	private ProdutoDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Produto> salvarProduto(@Valid @RequestBody Produto produto) {
		Produto produtoSalvo = new Produto();
		try {
			produtoSalvo = dao.save(produto);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(produtoSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(produtoSalvo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public ResponseEntity<?> atualizarProduto(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute AtualizarProdutoDto produto) {
		try {
			service.atualizarProduto(id, produto);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "{id}/excluir")
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
		List<Produto> produto = dao.pesquisarPorNome("%" + nome + "%");
		return ResponseEntity.ok(produto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Produto> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Produto produto = dao.getOne(id);
		return ResponseEntity.ok(produto);
	}

}
