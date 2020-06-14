package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.config.JwtTokenUtil;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.dto.RespostaJwt;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.AutenticacaoException;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.ServiceException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;
import br.com.feirasverdes.backend.service.UsuarioService;

@RestController
@CrossOrigin
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UsuarioService service;

	@RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
	public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario) throws ServiceException {
		try {
			service.salvarUsuario(usuario);
			return ResponseEntity.ok("Cadastrado com sucesso.");
		} catch (final EmailInvalidoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaDto(e.getMessage()));
		} catch (final TipoInvalidoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespostaDto(e.getMessage()));
		} catch (final Exception e) {
			throw new br.com.feirasverdes.backend.exception.ServiceException();
		}
	}

	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public ResponseEntity<?> atualizarCliente(@PathVariable(value = "id", required = true) Long id,
			@RequestBody Usuario usuario, MultipartFile foto) {
		try {
			service.atualizarUsuario(id, usuario, foto);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}

	}

	@RolesAllowed({ "ROLE_CONSUMIDOR", "ROLE_FEIRANTE", "ROLE_ORGANIZADOR" })
	@RequestMapping(method = RequestMethod.DELETE, value = "{id}/excluir")
	public ResponseEntity<?> excluir(@PathVariable(value = "id", required = true) Long id) {
		try {
			service.excluirUsuario(id);
			return ResponseEntity.ok("Excluído com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespostaDto(e.getMessage()));
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarTodos")
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(service.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome/{nome}")
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome") String nome) {
		return ResponseEntity.ok(service.pesquisarPorNome(nome));
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Usuario> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.of(service.pesquisarPorId(id));
	}

	@RequestMapping(method = RequestMethod.POST, value = "login")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) throws Exception {

		try {
			service.autenticar(usuario.getEmail(), usuario.getSenha());
			final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(usuario.getEmail());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new RespostaJwt(token));
		} catch (final BadCredentialsException | DisabledException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RespostaDto("Email ou senha inválidos"));
		} catch (final Exception e) {
			throw new AutenticacaoException();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "detalhes")
	public ResponseEntity<?> getDetalhes() throws Exception {
		// TODO implementar
		return null;
	}

}
