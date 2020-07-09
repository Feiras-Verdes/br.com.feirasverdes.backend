package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.dto.RecuperarSenhaDTO;
import br.com.feirasverdes.backend.dto.UsuarioDto;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private UsuarioDao dao;

	@Autowired
	private TipoUsuarioDao tipoUsuarioDao;

	@Autowired
	private AuthenticationManager auhenticationManager;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Usuario salvarUsuario(final Usuario usuario) throws EmailInvalidoException, TipoInvalidoException {

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
		return dao.save(usuario);
	}

	public void atualizarUsuario(final Long id, final UsuarioDto usuarioAtualizado) throws IOException {
		Usuario usuario = dao.getOne(id);

		if (usuarioAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = usuarioAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());
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
		return dao.pesquisarPorNome(nome.toUpperCase());
	}

	public Optional<Usuario> pesquisarPorId(Long id) {
		return dao.pesquisarPorId(id);
	}

	public DetalhesDoUsuarioDto getDetalhes() throws IOException, DataFormatException {
		final String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = dao.pesquisarPorEmail(email);

		if (usuario != null) {
			String dataFormatada = null;
			if (usuario.getDataNascimento() != null) {
				SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
				dataFormatada = formatador.format(usuario.getDataNascimento());
			}
			return DetalhesDoUsuarioDto.builder().withNome(usuario.getNome()).withEmail(usuario.getEmail())
					.withCpf(usuario.getCpf()).withCnpj(usuario.getCnpj()).withDataNascimento(dataFormatada)
					.withTelefone(usuario.getTelefone()).withTipoUsuario(usuario.getTipoUsuario())
					.withImagem(usuario.getImagem()).withId(usuario.getId()).build();
		} else {
			return null;
		}
	}

	public void alterarSenha(String novaSenha) {
		final String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuario = dao.pesquisarPorEmail(email);
		usuario.setSenha(passwordEncoder.encode(novaSenha));
		dao.save(usuario);
	}

	public SimpleMailMessage gerarSenha(RecuperarSenhaDTO recuperarSenhaDTO) throws EmailInvalidoException {
		Usuario usuario = dao.pesquisarPorEmail(recuperarSenhaDTO.getEmail());
		if (usuario != null) {
			String senha = UUID.randomUUID().toString().replace("-", "");
			usuario.setSenha(passwordEncoder.encode(senha));
			dao.save(usuario);

			SimpleMailMessage mensagem = new SimpleMailMessage();
			mensagem.setSubject("Feiras verdes - Nova senha");
			mensagem.setText("Segue abaixo a nova senha para acessar o sistema Feiras Verdes:\n " + senha);
			mensagem.setTo(usuario.getEmail());
			mensagem.setFrom("feirasverdes@gmail.com");

			return mensagem;
		}
		throw new EmailInvalidoException("Email não encontrado.");
	}

}
