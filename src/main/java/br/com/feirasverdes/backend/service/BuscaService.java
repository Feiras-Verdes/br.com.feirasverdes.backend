package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.util.ImagemUtils;

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
		List<EstabelecimentoDto> feiraImagem = new ArrayList<EstabelecimentoDto>();
		feiraImagem = feira.getContent();
		for(int i = 0; i < feiraImagem.size(); i++) {
				if (feiraImagem.get(i).getImagem() != null) {
					feiraImagem.get(i).getImagem().setBytesImagem(ImagemUtils.decompressBytes(feiraImagem.get(i).getImagem().getBytesImagem()));
				}
		}
		Page<EstabelecimentoDto> estabelecimento = new PageImpl(feiraImagem, feira.getPageable(), feira.getTotalElements());
		
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
