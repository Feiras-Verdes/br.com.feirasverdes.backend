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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dto.EstandeDetalheDto;
import br.com.feirasverdes.backend.dto.FeiraDetalheDTO;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.dto.ListFeiraDTO;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.exception.FeiraNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.service.AvaliacaoService;
import br.com.feirasverdes.backend.service.EstandeService;
import br.com.feirasverdes.backend.service.FeiraService;
import br.com.feirasverdes.backend.service.NoticiaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/feiras")
public class FeiraController {

	@Autowired
	private FeiraService service;

	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private NoticiaService noticiaService;

	@Autowired
	private EstandeService estandeService;

	@RolesAllowed({ "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Feira> salvarFeira(@Valid @RequestBody Feira feira) {
		try {
			return new ResponseEntity<>(service.cadastrarFeira(feira), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RolesAllowed({ "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
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
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirFeira(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (FeiraNaoPertenceAoUsuarioException e) {
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
	public ResponseEntity<FeiraDetalheDTO> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(service.pesquisarPorId(id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarAvaliacaoUsuario/{idUsuario}")
	public ResponseEntity<List> listarAvaliacaoUsuario(@PathVariable("idUsuario") Long idUsuario) {
		return ResponseEntity.ok(avaliacaoService.listarAvaliacaoPorUsuario(idUsuario));
	}

	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.POST, value = "{idFeira}/avaliar")
	public ResponseEntity<Avaliacao> salvarAvaliacaoFeira(@PathVariable(value = "idFeira") Long idFeira, @RequestBody Avaliacao avaliacaofeira) {
		try {
			return new ResponseEntity<>(avaliacaoService.avaliarFeira(idFeira, avaliacaofeira), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/*
	 * @RequestMapping(method = RequestMethod.GET, value =
	 * "{idFeira}/pesquisar-por-todas-estandes-da-feira/{nome}") public
	 * ResponseEntity<List<Estande>>
	 * pesquisarPorodosEstandesdaFeira(@PathVariable(value = "idFeira") Long
	 * idFeira,
	 * 
	 * @PathVariable(value = "nome") String nome) { List<Estande> estandes =
	 * estandeService.pesquisarPorFeiraENome(idFeira, "%" + nome.toUpperCase() +
	 * "%"); return ResponseEntity.ok(estandes); }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "{idFeira}/estandes")
	public ResponseEntity<List<EstandeDetalheDto>> estandesDaFeira(@PathVariable(value = "idFeira") Long idFeira) {
		return ResponseEntity.ok(estandeService.buscarEstandesDeFeira(idFeira));
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-melhores-feiras")
	public ResponseEntity<List<ListFeiraDTO>> pesquisarPorMelhoresFeiras() {
		return ResponseEntity.ok(service.buscarMelhoresFeiras());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{idFeira}/noticias")
	public ResponseEntity<List> pesquisarPorNoticiasDasFeiras(@PathVariable(value = "idFeira") Long idFeira) {
		return ResponseEntity.ok(noticiaService.buscarUltimasNoticiadaFeira(idFeira));
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET, value =
	 * "listar-por-organizador/{id}") public ResponseEntity<?>
	 * getFeirasDeFeirante(@PathVariable(value = "id") Long id) throws Exception {
	 * try { return ResponseEntity.ok(service.getFeirasDeOrganizador(id)); } catch
	 * (final UsuarioNaoEOrganizadorException e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
	 * RespostaDto(e.getMessage())); } }
	 */
	@RequestMapping(method = RequestMethod.GET, value = "ultimas-noticias")
	public ResponseEntity<List> ultimasNoticias() {
		return ResponseEntity.ok(noticiaService.buscarUltimasNoticias());
	}

}
