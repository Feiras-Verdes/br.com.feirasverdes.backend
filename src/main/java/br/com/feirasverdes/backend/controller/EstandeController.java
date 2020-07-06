package br.com.feirasverdes.backend.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.service.EstandeService;

@RestController
@CrossOrigin
@RequestMapping(value = "/estandes")
public class EstandeController {
	
	@Autowired
	private EstandeService service;
	
	@Autowired
	private AvaliacaoDao avaliacaoDao;
	
	@Autowired
	private EstandeDao dao;
	
	@Autowired
	private NoticiaDao noticiaDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Estande> salvarEstande(@Valid @RequestBody Estande estande) {
		Estande estandeSalva = new Estande();
		try {
			estandeSalva = dao.save(estande);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(estandeSalva, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(estandeSalva, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<?> atualizarEstande(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute EstandeDto estande) {
		try {
			service.atualizarEstande(id, estande);
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
		List<Estande> estandes = dao.pesquisarPorNome("%" + nome + "%");
		return ResponseEntity.ok(estandes);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<Estande> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Estande estande = dao.getOne(id);
		return ResponseEntity.ok(estande);
	}
	
	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST, value = "{id}/avaliar")
	public ResponseEntity<Avaliacao> salvarAvaliacaoFeira(@PathVariable(value = "id") Long idEstande, @RequestBody Avaliacao avaliacao) {
		Avaliacao cadastro = new Avaliacao();
		try {
			avaliacao.setEstande(new Estande(idEstande));
			cadastro = avaliacaoDao.save(avaliacao);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(cadastro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(cadastro, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}/noticias")
	public ResponseEntity<List> pesquisarPorNoticiasDasFeiras(@PathVariable(value = "id") Long idEstande) {
		List<Noticia> noticias = noticiaDao.buscarUltimasNoticiadaEstande(idEstande);
		return ResponseEntity.ok(noticias);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}/produtos")
	public ResponseEntity<List> produtosDoEstande(@PathVariable(value = "id") Long idEstande) {
		List<Produto> produtos = produtoDao.findByEstandeId(idEstande);
		return ResponseEntity.ok(produtos);
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
