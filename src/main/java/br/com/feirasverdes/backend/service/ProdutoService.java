package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.ProdutoDto;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.ProdutoNaoPertenceAoUsuarioException;

@Service
@Transactional
public class ProdutoService {

	@Autowired
	private ProdutoDao dao;

	public Produto cadastrarProduto(@Valid final ProdutoDto produtoDto) throws IOException {
		return dao.save(paraProduto(new Produto(), produtoDto));
	}

	public void atualizarProduto(final Long id, final ProdutoDto produtoAtualizado)
			throws IOException, ProdutoNaoPertenceAoUsuarioException {
		Usuario Usuario = dao.getOne(id).getEstande().getUsuario();

		if (!Usuario.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new ProdutoNaoPertenceAoUsuarioException("Este produto não foi cadastrado por você.");
		}

		dao.save(paraProduto(dao.getOne(id), produtoAtualizado));
	}

	public void excluirProduto(Long id) throws ProdutoNaoPertenceAoUsuarioException {
		Usuario Usuario = dao.getOne(id).getEstande().getUsuario();

		if (!Usuario.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new ProdutoNaoPertenceAoUsuarioException("Este produto não foi cadastrado por você.");
		}

		dao.deleteById(id);
	}

	private Produto paraProduto(final Produto produto, final ProdutoDto produtoDto) throws IOException {
		Produto p = produto;
		p.setNome(produtoDto.getNome());
		p.setDescricao(produtoDto.getDescricao());
		p.setPreco(produtoDto.getPreco());
		p.setUnidade(produtoDto.getUnidade());

		if (produtoDto.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = produtoDto.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());

			p.setImagem(imagem);
		}

		return p;
	}

	public List<Produto> listarTodos() {
		return dao.findAll();
	}

	public List<Produto> pesquisarPorNome(String nome) {
		return dao.pesquisarPorNome(nome);
	}

	public Produto pesquisarPorId(Long id) {
		return dao.getOne(id);
	}

	public List<Produto> listarProdutosDeEstande(Long idEstande) {
		return dao.findByEstandeId(idEstande);
	}
}
