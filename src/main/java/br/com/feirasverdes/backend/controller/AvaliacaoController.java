package br.com.feirasverdes.backend.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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

import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.exception.AvaliacaoNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.service.AvaliacaoService;

@RestController
@CrossOrigin
@RequestMapping(value = "/avaliacao")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService service;

	@RolesAllowed({ "ROLE_CONSUMIDOR" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Avaliacao> salvarAvaliacao(@Valid @RequestBody Avaliacao avaliacao) {
		return new ResponseEntity<>(service.avaliar(avaliacao), HttpStatus.OK);
	}

	@RolesAllowed({ "ROLE_CONSUMIDOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}")
	public ResponseEntity<?> atualizarAvaliacao(@Valid @PathVariable(value = "id", required = true) Long id,
			@RequestBody Avaliacao avaliacao) {
		try {
			service.atualizarAvaliacao(id, avaliacao);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (AvaliacaoNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
		
	}

	@RolesAllowed({ "ROLE_CONSUMIDOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}")
	public ResponseEntity excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirAvaliacao(id);
		} catch (AvaliacaoNaoPertenceAoUsuarioException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
		return ResponseEntity.ok("Avaliação excluída com sucesso");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public ResponseEntity<Avaliacao> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(service.pesquisarPorId(id));
	}

}
