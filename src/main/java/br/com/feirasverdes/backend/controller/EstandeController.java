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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.exception.EstandeNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.service.AvaliacaoService;
import br.com.feirasverdes.backend.service.EstandeService;
import br.com.feirasverdes.backend.service.NoticiaService;
import br.com.feirasverdes.backend.service.ProdutoService;

@RestController
@CrossOrigin
@RequestMapping(value = "/estandes")
public class EstandeController {

	@Autowired
	private EstandeService service;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private NoticiaService noticiaService;

	@Autowired
	private ProdutoService produtoService;

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Estande> salvarEstande(@Valid @RequestBody Estande estande) {
		return new ResponseEntity<>(service.cadastrarEstande(estande), HttpStatus.OK);
	}

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<?> atualizarEstande(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute EstandeDto estande) throws IOException {
		try {
			service.atualizarEstande(id, estande);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (EstandeNaoPertenceAoUsuarioException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
	}

	@RolesAllowed({ "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirEstande(id);
			return ResponseEntity.ok().build();
		} catch (EstandeNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
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
	public ResponseEntity<Estande> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(service.pesquisarPorId(id));
	}
	
	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST, value = "{id}/avaliar")
	public ResponseEntity<Avaliacao> salvarAvaliacaoFeira(@PathVariable(value = "id") Long idEstande, @RequestBody Avaliacao avaliacao) {
		try {
			return new ResponseEntity<>(avaliacaoService.avaliarEstande(idEstande, avaliacao), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/noticias")
	public ResponseEntity<List> pesquisarPorNoticiasDasFeiras(@PathVariable(value = "id") Long idEstande) {
		return ResponseEntity.ok(noticiaService.buscarUltimasNoticiadaEstande(idEstande));
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/produtos")
	public ResponseEntity<List> produtosDoEstande(@PathVariable(value = "id") Long idEstande) {
		return ResponseEntity.ok(produtoService.listarProdutosDeEstande(idEstande));
	}

	// Métodos que prentencem em outros controllers

//	@RequestMapping(method = RequestMethod.POST, value = "cadastrarAvaliacaoEstande")
//	public ResponseEntity<Avaliacao> salvarAvaliacaoEstande(@RequestBody Avaliacao avalicaoEstande) {
//		Avaliacao cadastro = new Avaliacao();
//		try {
//			cadastro = avaliacaoDao.save(avalicaoEstande);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(cadastro, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		return new ResponseEntity<>(cadastro, HttpStatus.OK);
//	}

//	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-todos-produtos-da-feira/{nome}")
//	public ResponseEntity<List> pesquisarPortodosProdutosdaFeira(@PathVariable(value = "nome") String nome) {
//		List<Estande> estandes = dao.pesquisarPorNome(nome);
//		return ResponseEntity.ok(estandes);
//	}

//	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-todas-noticias-da-feira/{nome}")
//	public ResponseEntity<List> pesquisarPortodasNoticiasdaEstande(@PathVariable(value = "nome") String nome) {
//		List<Estande> estandes = dao.pesquisarPorNome(nome);
//		return ResponseEntity.ok(estandes);
//	}

}
