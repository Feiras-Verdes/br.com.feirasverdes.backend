package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.AtualizarEstandeDto;
import br.com.feirasverdes.backend.dto.AtualizarProdutoDto;
import br.com.feirasverdes.backend.dto.AtualizarUsuarioDto;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;
import br.com.feirasverdes.backend.util.ImagemUtils;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoDao dao;

	public void atualizarProduto(final Long id, AtualizarProdutoDto produtoAtualizado) throws IOException{
		
		Produto produto = dao.getOne(id);
		
		if (produtoAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = produtoAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(ImagemUtils.compressBytes(foto.getBytes()));
			
			produto.setImagem(imagem);
		}
		produto.setNome(produtoAtualizado.getNome());
		produto.setDescricao(produtoAtualizado.getDescricao());
		produto.setPreco(produtoAtualizado.getPreco());
		dao.save(produto);
	}

}
