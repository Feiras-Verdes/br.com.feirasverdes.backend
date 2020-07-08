package br.com.feirasverdes.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.ProdutoDto;
import br.com.feirasverdes.backend.entidade.Produto;

@Service
@Transactional
public class ProdutoService {

	@Autowired
	private ProdutoDao dao;

	public void atualizarProduto(final Long id, ProdutoDto produtoAtualizado) throws IOException {

		Produto produto = dao.getOne(id);

		produto.setImagem(produtoAtualizado.getImagem());
		produto.setNome(produtoAtualizado.getNome());
		produto.setDescricao(produtoAtualizado.getDescricao());
		produto.setPreco(produtoAtualizado.getPreco());
		produto.setUnidade(produtoAtualizado.getUnidade());
		
		dao.save(produto);
	}

}
