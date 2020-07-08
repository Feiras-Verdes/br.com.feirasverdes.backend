package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dto.ProdutoDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.exception.ProdutoNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.service.ProdutoService;

@RestController
@CrossOrigin
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@RolesAllowed({ "ROLE_FEIRANTE" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Produto> salvarProduto(@Valid @ModelAttribute ProdutoDto produto) {
		Produto produtoSalvo = new Produto();
		try {
			produtoSalvo = service.cadastrarProduto(produto);
			return new ResponseEntity<>(produtoSalvo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(produtoSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RolesAllowed({ "ROLE_FEIRANTE" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<?> atualizarProduto(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute ProdutoDto produto) {
		try {
			service.atualizarProduto(id, produto);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		} catch (ProdutoNaoPertenceAoUsuarioException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaDto(e.getMessage()));
		}
	}

	@RolesAllowed({ "ROLE_FEIRANTE" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirProduto(id);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (ProdutoNaoPertenceAoUsuarioException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome")
	public ResponseEntity<List> pesquisarPorNome(@RequestParam(required = true) String nome) {
		return ResponseEntity.ok(service.pesquisarPorNome(nome));
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<Produto> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(service.pesquisarPorId(id));
	}

}
