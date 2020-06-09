package br.com.feirasverdes.backend.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.Usuario;

@RestController
@CrossOrigin
@RequestMapping(value = "/feirante")
public class FeiranteController {
							

	@Autowired		
	private UsuarioDao dao;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarUsuario(Usuario usuario) {
		dao.save(usuario);
		return Response.status(Status.CREATED).entity(usuario).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarUsuario(@PathParam("id") long id, @RequestBody Usuario Usuario) {
		dao.save(Usuario);
		return Response.ok().build();
	}
	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") long id) {
        dao.deleteById(id);
        return Response.ok().build();
    }
	@GET       
    @Produces(MediaType.APPLICATION_JSON)
	public Response listarTodos() {
		return Response.ok(dao.findAll()).build();
	}
	
	@GET
	@Path("{nome}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarPorNome(@PathParam("nome") String nome) {
		List<Usuario> usuarios = dao.pesquisarPorNome(nome);
		return Response.ok(usuarios).build();
	}
	@GET
	@Path("/Usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarPorId(@PathParam("id") long id) {
		Usuario usuario = dao.getOne(id);
		return Response.ok(usuario).build();
	}
	
	
}
