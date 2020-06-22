package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.AtualizarUsuarioDto;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;
import br.com.feirasverdes.backend.util.ImagemUtils;

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

	public void atualizarUsuario(final Long id, final AtualizarUsuarioDto usuarioAtualizado) throws IOException {
		Usuario usuario = dao.getOne(id);
		if (usuarioAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = usuarioAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(ImagemUtils.compressBytes(foto.getBytes()));

			usuario.setImagem(imagem);
		}
		usuario.setCnpj(usuarioAtualizado.getCnpj());
		usuario.setCpf(usuarioAtualizado.getCpf());
		usuario.setDataNascimento(usuarioAtualizado.getDataNascimento());
		usuario.setEmail(usuarioAtualizado.getEmail());
		usuario.setNome(usuarioAtualizado.getNome());
		usuario.setTelefone(usuarioAtualizado.getTelefone());
		dao.save(usuario);
	}

	public void excluirUsuario(final Long id) {
		Optional<Usuario> usuario = dao.pesquisarPorId(id);
		if (usuario.isPresent()) {
			usuario.get().setAtivo(false);
			dao.save(usuario.get());
		}
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

	public Optional<?> pesquisarPorId(Long id) {
		return dao.pesquisarPorId(id);
	}

	public DetalhesDoUsuarioDto getDetalhes(Long idUsuario) throws IOException, DataFormatException {
		final Optional<Usuario> usuario = dao.pesquisarPorId(idUsuario);

		if (usuario.isPresent()) {
			if (usuario.get().getImagem() != null) {
				usuario.get().getImagem()
						.setBytesImagem(ImagemUtils.decompressBytes(usuario.get().getImagem().getBytesImagem()));
			}
			return DetalhesDoUsuarioDto.builder().withNome(usuario.get().getNome()).withEmail(usuario.get().getEmail())
					.withCpf(usuario.get().getCpf()).withCnpj(usuario.get().getCnpj())
					.withDataNascimento(usuario.get().getDataNascimento()).withTelefone(usuario.get().getTelefone())
					.withTipoUsuario(usuario.get().getTipoUsuario()).withImagem(usuario.get().getImagem())
					.withId(usuario.get().getId()).build();
		} else {
			return null;
		}
	}

}
