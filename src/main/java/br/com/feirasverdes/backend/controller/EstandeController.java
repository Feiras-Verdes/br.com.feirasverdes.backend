package br.com.feirasverdes.backend.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dto.AtualizarEstandeDto;
import br.com.feirasverdes.backend.dto.RespostaDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.service.EstandeService;

@RestController
@CrossOrigin
@RequestMapping(value = "/estande")
public class EstandeController {
	
	@Autowired
	EstandeService service;
	
	@Autowired
	private EstandeDao dao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarEstande(Estande estande) {
		dao.save(estande);
		return Response.status(Status.CREATED).entity(estande).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "{id}/atualizar")
	public ResponseEntity<?> atualizarEstande(@PathVariable(value = "id", required = true) Long id,
			@ModelAttribute AtualizarEstandeDto estande) {
		try {
			service.atualizarEstande(id, estande);
			return ResponseEntity.ok("Atualizado com sucesso.");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new RespostaDto(e.getMessage()));
		}
	}

	@DELETE
	@Path("{id}")
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
		List<Estande> Estandes = dao.pesquisarPorNome("%" + nome +"%");
		return Response.ok(Estandes).build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarPorId(@PathParam("id") long id) {
		Estande estandes = dao.getOne(id);
		return Response.ok(estandes).build();
	}
}
