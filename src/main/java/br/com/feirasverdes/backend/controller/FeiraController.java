package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.exception.FeiraNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.exception.UsuarioNaoEOrganizadorException;
import br.com.feirasverdes.backend.service.FeiraService;

@RestController
@CrossOrigin
@RequestMapping(value = "/feiras")
public class FeiraController {

	@Autowired
	private FeiraService service;

	@Autowired
	private AvaliacaoDao avaliacaodao;

	@Autowired
	private NoticiaDao noticiadao;

	@Autowired
	private EstandeDao estandedao;

	@Autowired
	private FeiraDao dao;

	@RolesAllowed({ "ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST, value = "cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Feira> salvarFeira(@Valid @RequestBody Feira feira) {
		try {
			return new ResponseEntity<>(service.cadastrarFeira(feira), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RolesAllowed({ "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public ResponseEntity<?> atualizarFeira(@Valid @PathVariable(value = "id", required = true) Long id,
			@ModelAttribute FeiraDto feira) throws IOException {
		try {
			feira.setId(id);
			return new ResponseEntity<>(service.atualizarFeira(feira), HttpStatus.OK);
		} catch (FeiraNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaDto(e.getMessage()));
		}
	}

	@RolesAllowed({ "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}/excluir")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirFeira(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (FeiraNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarTodos")
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(dao.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome/{nome}")
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome", required = true) String nome) {
		List<Feira> feiras = dao.pesquisarPorNome("%" + nome.toUpperCase() + "%");
		return ResponseEntity.ok(feiras);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<Feira> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Feira feira = dao.getOne(id);
		return ResponseEntity.ok(feira);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarAvaliacaoUsuario/{idUsuario}")
	public ResponseEntity<List> listarAvaliacaoUsuario(@PathVariable("idUsuario") Long idUsuario) {
		return ResponseEntity.ok(avaliacaodao.findByUsuarioId(idUsuario));
	}

	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
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
	public ResponseEntity<List<Estande>> pesquisarPorodosEstandesdaFeira(@PathVariable(value = "idFeira") Long idFeira,
			@PathVariable(value = "nome") String nome) {
		List<Estande> estandes = estandedao.pesquisarPorFeiraENome(idFeira, "%" + nome.toUpperCase() + "%");
		return ResponseEntity.ok(estandes);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-melhores-feiras")
	public ResponseEntity<List> pesquisarPorMelhoresFeiras() {
		List<Feira> feiras = dao.buscarMelhoresFeiras();
		return ResponseEntity.ok(feiras);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-noticias-feiras/{idFeira}")
	public ResponseEntity<List> pesquisarPorNoticiasDasFeiras(@PathVariable(value = "idFeira") Long idFeira) {
		List<Noticia> noticiadasfeiras = noticiadao.buscarUltimasNoticiadaFeira(idFeira);
		return ResponseEntity.ok(noticiadasfeiras);
	}

	@RequestMapping(method = RequestMethod.GET, value = "listar-por-organizador/{id}")
	public ResponseEntity<?> getFeirasDeFeirante(@PathVariable(value = "id") Long id) throws Exception {
		try {
			return ResponseEntity.ok(service.getFeirasDeOrganizador(id));
		} catch (final UsuarioNaoEOrganizadorException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaDto(e.getMessage()));
		}
	}

}
