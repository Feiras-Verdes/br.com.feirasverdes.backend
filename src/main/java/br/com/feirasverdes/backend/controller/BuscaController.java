package br.com.feirasverdes.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import br.com.feirasverdes.backend.exception.AutenticacaoException;

@RestController
@CrossOrigin
@RequestMapping(value = "/busca")
public class BuscaController {
	
	@Autowired
	private EstandeDao estanteDao;
	
	@GetMapping("/estabelecimentos")
	@ResponseBody
	public ResponseEntity<?> buscaEstabelecimento(
			@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, 
			@RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao,
			@RequestParam(required = false) String tipoOrdenacao) throws Exception {
		try {
			String filtros = criarOrdenacao(ordenacao, tipoOrdenacao);
//			final Page<EstabelecimentoDto> estabelecimentos;
//			estabelecimentos = estanteDao.buscaEstandePorFiltro("%" + nome + "%",  PageRequest.of(getOffset(limite, pagina),limite));
			final Page<EstabelecimentoDto> estabelecimentos;
			estabelecimentos = estanteDao.buscaEstandePorFiltro("%" + nome.toUpperCase() + "%",  PageRequest.of(pagina ,limite));
			return ResponseEntity.ok(estabelecimentos);
			
		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Email ou senha inválidos"));
		} catch (final Exception e) {
			throw new AutenticacaoException();
		}
	}
	
	public String criarOrdenacao(String ordenacao, String tipoOrdenacao) {
		String query= "";
		if(!ordenacao.isEmpty() && ordenacao != null) {
			query += " order by " + ordenacao;
		}
		if(!tipoOrdenacao.isEmpty() && tipoOrdenacao != null) {
			query += " " + tipoOrdenacao;
		}
		return query;
	}
	
	public Integer getOffset(Integer limite, Integer pagina) {
		if (limite != null && pagina != null) {
			return (limite * (pagina - 1));
		} else {
			return null;
		}
	}

}
