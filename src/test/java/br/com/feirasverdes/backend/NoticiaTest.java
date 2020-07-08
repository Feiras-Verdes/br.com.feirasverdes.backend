package br.com.feirasverdes.backend;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.service.FeiraService;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class NoticiaTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NoticiaDao dao;

	@Autowired
	private UsuarioTestUtil usuarioTestUtil;

	@Autowired
	private FeiraService feiraService;

	@Autowired
	private EstandeDao estandeDao;

	private Feira feira;

	private Estande estande;

	private Usuario usuario;

	@BeforeEach
	public void iniciar() throws IOException {
		usuario = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
		Feira f = FeiraTest.criarFeiraDtoSemUsuario();
		f.setUsuario(usuario);
		feira = feiraService.cadastrarFeira(f);
		estande = estandeDao.save(EstandeTest.criarEstande());
	}

	@Test
	@Transactional
	public void testCadastrarNoticia() throws IOException, Exception {
		Noticia noticia = criarNoticia();
		MvcResult result = mockMvc
				.perform(post("/noticias/cadastrar").headers(TestUtil.autHeaders())
				.param("titulo", noticia.getTitulo())
				.param("descricao", noticia.getDescricao()))
				.andExpect(status().isOk()).andReturn();
		Noticia noticiaResult = (Noticia) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(),
				Noticia.class);

		Noticia noticiaSalvo = dao.getOne(noticiaResult.getId());
		assertNotNull(noticiaSalvo);
		assertEquals(noticia.getTitulo(), noticiaSalvo.getTitulo());
		assertEquals(noticia.getDescricao(), noticiaSalvo.getDescricao());
	}

	@Test
	@Transactional
	public void testAtualizarNoticia() throws IOException, Exception {
		Noticia noticia = criarNoticia();
		Noticia noticiaCadastrada = dao.save(noticia);
		noticia.setId(noticiaCadastrada.getId());
		noticia.setTitulo("Chegou bananas");

		mockMvc.perform(put("/noticias/" + noticia.getId() + "/atualizar").headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(noticia)))
				.andExpect(status().isOk());

		Noticia noticiaSalva = dao.getOne(noticiaCadastrada.getId());
		assertEquals(noticia.getTitulo(), noticiaSalva.getTitulo());

	}

	@Test
	@Transactional
	public void testExcluirNoticia() throws Exception {
		Noticia noticia = criarNoticia();
		Noticia noticiaCadastrada = dao.save(noticia);

		mockMvc.perform(delete("/noticias/" + noticiaCadastrada.getId() + "/excluir").headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		assertFalse(dao.findById(noticiaCadastrada.getId()).isPresent());
	}

	@Test
	public void testListarTodos() throws Exception {
		Noticia noticia = criarNoticia();
		noticia.setTitulo("BBB");
		dao.saveAndFlush(noticia);
		mockMvc.perform(get("/noticias/listarTodos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].titulo").value(hasItem(noticia.getTitulo())));
	}

	@Test
	@Transactional
	public void testPesquisarPorId() throws Exception {
		Noticia noticia = criarNoticia();
		noticia.setTitulo("CCC");
		Noticia noticiaCadastrada = dao.saveAndFlush(noticia);
		mockMvc.perform(
				get("/noticias/pesquisar-por-id/" + noticiaCadastrada.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.titulo").value(noticia.getTitulo()));
	}

	private Noticia criarNoticia() {
		Noticia noticia = new Noticia();
		noticia.setTitulo("Chegou laranja");
		noticia.setDescricao("A laranja chegou no estande");
		noticia.setFeira(feira);
		noticia.setEstande(estande);
		noticia.setDataPublicacao(LocalDateTime.now());
		return noticia;

	}
}
