package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao dao;

	@Autowired
	private TipoUsuarioDao tipoUsuarioDao;

	@Autowired
	private AuthenticationManager auhenticationManager;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void salvarUsuario(final Usuario usuario) throws EmailInvalidoException, TipoInvalidoException {

		Usuario mesmoEmail = dao.pesquisarPorEmail(usuario.getEmail());
		if (mesmoEmail != null) {
			throw new EmailInvalidoException("Email já cadastrado.");
		}

		TipoUsuario tipo = tipoUsuarioDao.getOne(usuario.getTipoUsuario().getId());
		if (tipo == null) {
			throw new TipoInvalidoException("Tipo de usuário inválido.");
		}

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuario.setAtivo(true);
		dao.save(usuario);
	}

	public void atualizarUsuario(final Long id, final Usuario usuario, final MultipartFile foto) throws IOException {
		if (foto != null) {
			Imagem imagem = new Imagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());
		}
		usuario.setId(id);
		usuario.setAtivo(true);
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		dao.save(usuario);
	}

	public void excluirUsuario(final Long id) {
		Usuario usuario = dao.getOne(id);
		usuario.setAtivo(false);
		dao.save(usuario);
	}

	public List<?> listarTodos() {
		return dao.listarTodos();
	}

	public void autenticar(String email, String senha) {
		auhenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
	}

	public List<?> pesquisarPorNome(String nome) {
		return dao.pesquisarPorNome(nome);
	}

	public Optional pesquisarPorId(Long id) {
		return dao.pesquisarPorId(id);
	}

}
