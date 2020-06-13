package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Usuario;

@RestController
@CrossOrigin
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDao dao;

	@Autowired
	private TipoUsuarioDao daoTipo;

	@RequestMapping(method = RequestMethod.POST, value = "/cadastrar")
	public ResponseEntity<Usuario> salvarConsumidor(@RequestBody Usuario usuario) {
		Usuario usuarioSalvo = new Usuario();
		try {
			usuario.setAtivo(true);
			// usuario.setTipoUsuario(daoTipo.getOne((long) 1));
			usuarioSalvo = dao.save(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public Response atualizarCliente(@PathVariable(value = "id", required = true) Long id, @RequestBody Usuario usuario,
			MultipartFile foto) {
		try {
			Imagem imagem = new Imagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());
			usuario.setId(id);
			dao.save(usuario);
			return Response.ok().build();
		} catch (IOException e) {
			return Response.serverError().build();
		}

	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/excluir")
	public Response excluir(@PathVariable(value = "id", required = true) Long id) {
		Usuario usuario = dao.getOne(id);
		usuario.setAtivo(false);
		dao.save(usuario);
		return Response.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarTodos")
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(dao.listarTodos());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome/{nome}")
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome") String nome) {
		return ResponseEntity.ok(dao.pesquisarPorNome(nome));
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Usuario> pesquisarPorId(@PathVariable(value = "id") Long id) {
		return ResponseEntity.of(dao.pesquisarPorId(id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<?> login(@RequestBody Usuario usuario) {

		return (ResponseEntity<?>) ResponseEntity.ok();
	}

}
