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

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.entidade.Estande;

@RestController
@CrossOrigin
@RequestMapping(value = "/estande")
public class EstandeController {


	@Autowired
	private EstandeDao dao;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarEstande(Estande estande) {
		dao.save(estande);
		return Response.status(Status.CREATED).entity(estande).build();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarEstande(@PathParam("id") long id, @RequestBody Estande Estande) {
		dao.save(Estande);
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
		List<Estande> Estandes = dao.pesquisarPorNome(nome);
		return Response.ok(Estandes).build();
	}
	@GET
	@Path("/Estande/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarPorId(@PathParam("id") long id) {
		Estande estandes = dao.getOne(id);
		return Response.ok(estandes).build();
	}
}
