package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.service.FeiraService;

@RestController
@CrossOrigin
@RequestMapping(value = "/feira")
public class FeiraController {

	@Autowired
	private FeiraService service;
	
	@Autowired
	private FeiraDao dao;
	
	@Autowired
	private AvaliacaoDao avaliacaodao;
	
	@Autowired
	private NoticiaDao noticiadao;
	
	@Autowired
	private EstandeDao estandedao;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Feira> salvarFeira(@Valid @RequestBody Feira feira) {
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
	public Response atualizarFeira(@Valid @PathVariable(value = "id", required = true) Long id, @RequestBody FeiraDto feira) throws IOException {
		feira.setId(id);
		service.atualizarFeira(feira);
		return Response.ok().build();
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
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome", required = true) String nome) {
		List<Feira> feiras = dao.pesquisarPorNome("%" + nome + "%" );
		return ResponseEntity.ok(feiras);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Feira> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Feira feira = dao.getOne(id);
		return ResponseEntity.ok(feira);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listarAvaliacaoUsuario/{idUsuario}")
	public ResponseEntity<List> listarAvaliacaoUsuario(@PathVariable("idUsuario") Long idUsuario) {
		return ResponseEntity.ok(avaliacaodao.findByUsuarioId(idUsuario));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "cadastrarAvaliacaoFeira")
	public ResponseEntity<Avaliacao> salvarAvaliacaoFeira(@RequestBody Avaliacao avaliacaofeira) {
		Avaliacao cadastro = new Avaliacao();
		try {
			cadastro = avaliacaodao.save(avaliacaofeira);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(cadastro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(cadastro, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{idFeira}/pesquisar-por-todas-estandes-da-feira/{nome}")
	public ResponseEntity<List<Estande>> pesquisarPortodosEstandesdaFeira(@PathVariable(value = "idFeira") Long idFeira, @PathVariable(value = "nome") String nome) {
		List<Estande> estandes = estandedao.pesquisarPorFeiraENome(idFeira, "%"+nome+"%");
		return ResponseEntity.ok(estandes);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-melhores-feiras")
	public ResponseEntity<List> pesquisarPorMelhoresFeiras() {
		List<Feira> feiras = dao.buscarMelhoresFeiras();
		return ResponseEntity.ok(feiras);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-noticias-feiras/{idFeira}")
	public ResponseEntity<List> pesquisarPorNoticiasdasFeiras(@PathVariable(value = "idFeira") Long idFeira) {
		List<Noticia> noticiadasfeiras = noticiadao.buscarUltimasNoticiadaFeira(idFeira);
		return ResponseEntity.ok(noticiadasfeiras);
	}
}
