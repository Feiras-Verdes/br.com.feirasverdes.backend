package br.com.feirasverdes.backend;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.dto.UsuarioDto;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.service.UsuarioService;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class UsuarioTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioTestUtil usuarioTestUtil;
	
	private Usuario usuarioLogado;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@BeforeEach
	public void iniciar() {
		usuarioLogado = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
	}

	@Test
	public void testCadastarUsuario() throws IOException, Exception {
		Usuario usuario = criarUsuario("A", "a@localhost");
		usuario.setEmail("usuariocadastrar@localhost");
		mockMvc.perform(post("/usuarios/cadastrar")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(usuario)))
				.andExpect(status().isOk())
				.andReturn();
		Usuario usuarioSalvo = usuarioDao.pesquisarPorEmail(usuario.getEmail());
		assertUsuario(usuario, usuarioSalvo);
	}
	
	@Test
	public void testAtualizarUsuario() throws IOException, Exception {
		Usuario usuario = criarUsuario("B", "b@localhost");
		Usuario usuarioCadastrado = service.salvarUsuario(usuario);
		
		UsuarioDto usuarAtualizarUsuarioDto = criarAtualizarUsuarioDto();
		usuarAtualizarUsuarioDto.setNome("Usuario 2");
		usuarAtualizarUsuarioDto.setEmail("usuarioatualizar@localhost");
		usuarAtualizarUsuarioDto.setDataNascimento(null);
		mockMvc.perform(put("/usuarios/"+usuarioCadastrado.getId()+"/atualizar")
				.headers(TestUtil.autHeaders())
				.param("nome", usuarAtualizarUsuarioDto.getNome())
				.param("cnpj", usuarAtualizarUsuarioDto.getCnpj())
				.param("cpf", usuarAtualizarUsuarioDto.getCpf())
				.param("telefone", usuarAtualizarUsuarioDto.getTelefone())
				.param("email", usuarAtualizarUsuarioDto.getEmail())
				.param("ativo", String.valueOf(usuarAtualizarUsuarioDto.isAtivo())))
				.andExpect(status().isOk())
				.andReturn();
		Usuario usuarioSalvo = service.pesquisarPorId(usuarioCadastrado.getId()).get();
		assertUsuario(usuarAtualizarUsuarioDto, usuarioSalvo);
	}
	
	@Test
	public void testExcluirUsuario() throws IOException, Exception {
		Usuario usuario = criarUsuario("C", "c@localhost");
		Usuario usuarioCadastrado = service.salvarUsuario(usuario);
		
		mockMvc.perform(delete("/usuarios/"+usuarioCadastrado.getId()+"/excluir")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertFalse(service.pesquisarPorId(usuarioCadastrado.getId()).isPresent());
	}
	
	@Test
	public void testListarTodos() throws Exception {
		Usuario usuario = criarUsuario("D", "d@localhost");
		Usuario usuarioCadastrado = service.salvarUsuario(usuario);
		mockMvc.perform(get("/usuarios/listarTodos").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].nome").value(hasItem(usuarioCadastrado.getNome())));
	}
	
	@Test
	public void testPesquisarPorNome() throws Exception {
		Usuario usuario = criarUsuario("EAB", "e@localhost");
		Usuario usuarioCadastrado = service.salvarUsuario(usuario);
		mockMvc.perform(get("/usuarios/pesquisar-por-nome/ea").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].nome").value(usuarioCadastrado.getNome()));
	}
	
	@Test
	public void testPesquisarPorId() throws Exception {
		Usuario usuario = criarUsuario("F", "f@localhost");
		Usuario usuarioCadastrado = service.salvarUsuario(usuario);
		MvcResult result = mockMvc.perform(get("/usuarios/pesquisar-por-id/"+usuarioCadastrado.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		Usuario usuarioRetorno = (Usuario) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(), Usuario.class);
		usuarioCadastrado.setSenha("123");
		assertUsuario(usuarioCadastrado, usuarioRetorno);
	}
	
	@Test
	public void testLogin() throws IOException, Exception {
		service.salvarUsuario(criarUsuario("G", "g@localhost"));
		Usuario usuarioLogin = new Usuario();
		usuarioLogin.setEmail("g@localhost");
		usuarioLogin.setSenha("123");
		
		MvcResult result = mockMvc.perform(post("/usuarios/login")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(usuarioLogin)))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse().getContentAsString().contains("token"));
	}
	
	@Test
	public void testGetDetalhes() throws Exception {
		MvcResult result = mockMvc.perform(get("/usuarios/detalhes")
				.headers(TestUtil.autHeaders())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		DetalhesDoUsuarioDto usuarioRetorno = (DetalhesDoUsuarioDto) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(), DetalhesDoUsuarioDto.class);
		assertDetalheUsuario(usuarioRetorno, usuarioLogado);
	}

	private void assertUsuario(Usuario usuario, Usuario usuarioSalvo) {
		assertNotNull(usuarioSalvo);
		assertEquals(usuario.getNome(), usuarioSalvo.getNome());
		assertEquals(usuario.getCnpj(), usuarioSalvo.getCnpj());
		assertEquals(usuario.getCpf(), usuarioSalvo.getCpf());
		assertEquals(usuario.getDataNascimento(), usuarioSalvo.getDataNascimento());
		assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
		assertTrue(passwordEncoder.matches(usuario.getSenha(), usuarioSalvo.getSenha()));
		assertEquals(usuario.getTelefone(), usuarioSalvo.getTelefone());
		assertEquals(usuario.getTipoUsuario().getId(), usuarioSalvo.getTipoUsuario().getId());
	}
	
	private void assertDetalheUsuario(DetalhesDoUsuarioDto usuario, Usuario usuarioSalvo) {
		assertNotNull(usuarioSalvo);
		assertEquals(usuario.getNome(), usuarioSalvo.getNome());
		assertEquals(usuario.getCnpj(), usuarioSalvo.getCnpj());
		assertEquals(usuario.getCpf(), usuarioSalvo.getCpf());
		assertEquals(usuario.getDataNascimento(), usuarioSalvo.getDataNascimento());
		assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
		assertEquals(usuario.getTelefone(), usuarioSalvo.getTelefone());
		assertEquals(usuario.getTipoUsuario().getId(), usuarioSalvo.getTipoUsuario().getId());
	}

	private void assertUsuario(UsuarioDto usuario, Usuario usuarioSalvo) {
		assertNotNull(usuarioSalvo);
		assertEquals(usuario.getNome(), usuarioSalvo.getNome());
		assertEquals(usuario.getCnpj(), usuarioSalvo.getCnpj());
		assertEquals(usuario.getCpf(), usuarioSalvo.getCpf());
		assertEquals(usuario.getDataNascimento(), usuarioSalvo.getDataNascimento());
		assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
		assertEquals(usuario.getTelefone(), usuarioSalvo.getTelefone());
	}
	
	private Usuario criarUsuario(String nome, String email) {
		Usuario  usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setCpf("000.000.000-01");
		usuario.setDataNascimento(Date.from(LocalDate.of(2020, 6, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		usuario.setEmail(email);
		usuario.setSenha("123");
		usuario.setTelefone("(00) 0000-0000");
		usuario.setTipoUsuario(new TipoUsuario(1L));
		return usuario;
	}
	
	private UsuarioDto criarAtualizarUsuarioDto() {
		UsuarioDto  usuario = new UsuarioDto();
		usuario.setNome("Atualizar");
		usuario.setCpf("000.000.000-01");
		usuario.setDataNascimento(Date.from(LocalDate.of(2020, 6, 21).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		usuario.setEmail("teste1@email.com");
		usuario.setTelefone("(00) 0000-0000");
		return usuario;
	}
}
