package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.entidade.Produto;

@Service
@Transactional
public class BuscaService {
	
	@Autowired
	private EstandeDao estanteDao;

	@Autowired
	private FeiraDao feiraDao;
	
	@Autowired
	private ProdutoDao produtoDao;
	
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

	public Page<EstabelecimentoDto> verificarOrdenacaoFeira(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) throws IOException, DataFormatException {
		Page<EstabelecimentoDto> feira = null;
		if (!tipoOrdenacao.isEmpty() && tipoOrdenacao != null) {
			if (tipoOrdenacao.toUpperCase().equals("ASC")) {
				feira = feiraDao.buscaFeiraPorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.ASC, ordenacao));
			}
			if (tipoOrdenacao.toUpperCase().equals("DESC")) {
				feira = feiraDao.buscaFeiraPorFiltro("%" + nome.toUpperCase() + "%",
						PageRequest.of(pagina, limite, Sort.Direction.DESC, ordenacao));
			}
		}		
		return feira;
	}

	public Page<Produto> verificarOrdenacaoProduto(String nome, Integer limite, Integer pagina, String ordenacao,
			String tipoOrdenacao) throws IOException, DataFormatException {
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
