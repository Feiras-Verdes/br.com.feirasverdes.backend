package br.com.feirasverdes.backend.controller;

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

import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.Usuario;

@RestController
@CrossOrigin
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioDao dao;

	@RequestMapping(method = RequestMethod.POST, value = "cadastrar")
	public ResponseEntity<Usuario> salvarCliente(@RequestBody Usuario usuario) {
		Usuario usuarioSalvo = new Usuario();
		try {
			usuarioSalvo = dao.save(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(usuarioSalvo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public Response atualizarCliente(@PathVariable(value = "id", required = true) Long id,
			@RequestBody Usuario usuario) {
		usuario.setId(id);
		dao.save(usuario);
		return Response.ok().build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/excluir")
	public Response excluir(@PathVariable(value = "id", required = true) Long id) {
		dao.deleteById(id);
		return Response.ok().build();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarTodos")
	public ResponseEntity<List> listarTodos() {
		return ResponseEntity.ok(dao.findAll());
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-nome/{nome}")
	public ResponseEntity<List> pesquisarPorNome(@PathVariable(value = "nome") String nome) {
		List<Usuario> usuarios = dao.pesquisarPorNome(nome);
		return ResponseEntity.ok(usuarios);
	}

	@RequestMapping(method = RequestMethod.GET, value = "pesquisar-por-id/{id}")
	public ResponseEntity<Usuario> pesquisarPorId(@PathVariable(value = "id") Long id) {
		Usuario usuario = dao.getOne(id);
		return ResponseEntity.ok(usuario);
	}

}
