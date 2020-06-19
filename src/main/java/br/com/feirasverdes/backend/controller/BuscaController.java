package br.com.feirasverdes.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.exception.AutenticacaoException;

@RestController
@CrossOrigin
@RequestMapping(value = "/busca")
public class BuscaController {

	@Autowired
	private EstandeDao estanteDao;

	@GetMapping("/estabelecimentos")
	@ResponseBody
	public ResponseEntity<?> buscaEstabelecimento(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, @RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao, @RequestParam(required = false) String tipoOrdenacao)
			throws Exception {
		try {
			Page<Estande> estande = verificarOrdenacao(nome, limite, pagina, ordenacao, tipoOrdenacao);

			return ResponseEntity.ok(estande);

		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Email ou senha inválidos"));
		} catch (final Exception e) {
			throw new AutenticacaoException();
		}
	}

	public Page<Estande> verificarOrdenacao(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) {
		Page<Estande> estande = null;
		if (!tipoOrdenacao.isEmpty() && tipoOrdenacao != null) {
			if (tipoOrdenacao.toUpperCase().equals("ASC")) {
				estande = estanteDao.buscaEstandePorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.ASC, ordenacao));
			}
			if (tipoOrdenacao.toUpperCase().equals("DESC")) {
				estande = estanteDao.buscaEstandePorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.DESC, ordenacao));
			}
		}
		return estande;
	}

}
