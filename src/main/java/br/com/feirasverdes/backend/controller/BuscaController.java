package br.com.feirasverdes.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.exception.AutenticacaoException;

@RestController
@CrossOrigin
@RequestMapping(value = "/busca")
public class BuscaController {

	@Autowired
	private EstandeDao estanteDao;

	@Autowired
	private FeiraDao feiraDao;
	
	@Autowired
	private ProdutoDao produtoDao;

	@GetMapping("/estabelecimentos")
	@ResponseBody
	public ResponseEntity<?> buscaEstabelecimento(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, @RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao, @RequestParam(required = false) String tipoOrdenacao)
			throws Exception {
		try {
			Page<EstabelecimentoDto> estabelecimento = verificarOrdenacaoEstande(nome, limite, pagina, ordenacao, tipoOrdenacao);

			return ResponseEntity.ok(estabelecimento);

		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Busca de estabelecimento inválida"));
		} catch (final Exception e) {
			throw new AutenticacaoException();
		}
	}
	
	@GetMapping("/produtos")
	@ResponseBody
	public ResponseEntity<?> buscaProduto(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, @RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao, @RequestParam(required = false) String tipoOrdenacao)
			throws Exception {
		try {
			
			final Page<Produto> produto = verificarOrdenacaoProduto(nome, limite, pagina, ordenacao, tipoOrdenacao);

			return ResponseEntity.ok(produto);

		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Busca de produtos inválida"));
		} catch (final Exception e) {
			throw new AutenticacaoException();
		}
	}

	public Page<EstabelecimentoDto> verificarOrdenacaoEstande(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) {
		Page<EstabelecimentoDto> estande = null;
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

	public Page<Feira> verificarOrdenacaoFeira(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) {
		Page<Feira> feira = null;
		if (!tipoOrdenacao.isEmpty() && tipoOrdenacao != null) {
			if (tipoOrdenacao.toUpperCase().equals("ASC")) {
				feira = feiraDao.buscaEstandePorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.ASC, ordenacao));
			}
			if (tipoOrdenacao.toUpperCase().equals("DESC")) {
				feira = feiraDao.buscaEstandePorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.DESC, ordenacao));
			}
		}
		return feira;
	}

	public Page<Produto> verificarOrdenacaoProduto(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) {
		Page<Produto> produto = null;
		if (!tipoOrdenacao.isEmpty() && tipoOrdenacao != null) {
			if (tipoOrdenacao.toUpperCase().equals("ASC")) {
				produto = produtoDao.buscaProdutoPorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.ASC, ordenacao));
			}
			if (tipoOrdenacao.toUpperCase().equals("DESC")) {
				produto = produtoDao.buscaProdutoPorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.DESC, ordenacao));
			}
		}
		return produto;
	}
	
}
