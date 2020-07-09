package br.com.feirasverdes.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.exception.AutenticacaoException;
import br.com.feirasverdes.backend.service.BuscaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/busca")
public class BuscaController {

	@Autowired
	private BuscaService service;

	@GetMapping("/estandes")
	@ResponseBody
	public ResponseEntity<?> buscaEstande(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, @RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao, @RequestParam(required = false) String tipoOrdenacao)
			throws Exception {
		try {
			if (nome == null) nome = "";
			if (limite == null) limite = 50;
			if (pagina == null) pagina = 0;
			if (ordenacao == null || ordenacao.length() == 0) ordenacao = "nome";
			if (tipoOrdenacao == null || tipoOrdenacao.length() == 0) tipoOrdenacao = "asc";

			Page<EstabelecimentoDto> estande = service.verificarOrdenacaoEstande(nome, limite, pagina, ordenacao,
					tipoOrdenacao);

			return ResponseEntity.ok(estande);
		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new RespostaDto("Busca de estabelecimento inválida"));
		} catch (final Exception e) {
			e.printStackTrace();
			throw new AutenticacaoException();
		}
	}

	@GetMapping("/feiras")
	@ResponseBody
	public ResponseEntity<?> buscaFeira(@RequestParam(required = false) String nome,
			@RequestParam(required = false) Integer limite, @RequestParam(required = false) Integer pagina,
			@RequestParam(required = false) String ordenacao, @RequestParam(required = false) String tipoOrdenacao)
			throws Exception {
		try {
			if (nome == null) nome = "";
			if (limite == null) limite = 50;
			if (pagina == null) pagina = 0;
			if (ordenacao == null || ordenacao.length() == 0) ordenacao = "nome";
			if (tipoOrdenacao == null || tipoOrdenacao.length() == 0) tipoOrdenacao = "asc";
			
			Page<EstabelecimentoDto> feira = service.verificarOrdenacaoFeira(nome, limite, pagina, ordenacao,
					tipoOrdenacao);

			return ResponseEntity.ok(feira);
		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new RespostaDto("Busca de estabelecimento inválida"));
		} catch (final Exception e) {
			e.printStackTrace();
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
			if (nome == null) nome = "";
			if (limite == null) limite = 50;
			if (pagina == null) pagina = 0;
			if (ordenacao == null || ordenacao.length() == 0) ordenacao = "nome";
			if (tipoOrdenacao == null || tipoOrdenacao.length() == 0) tipoOrdenacao = "asc";

			final Page<Produto> produto = service.verificarOrdenacaoProduto(nome, limite, pagina, ordenacao,
					tipoOrdenacao);

			return ResponseEntity.ok(produto);
		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Busca de produtos inválida"));
		} catch (final Exception e) {
			e.printStackTrace();
			throw new AutenticacaoException();
		}
	}
}
