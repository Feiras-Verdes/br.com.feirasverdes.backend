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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.service.FeiraService;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AvaliacaoTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioTestUtil usuarioTestUtil;

	private Usuario usuario;

	private Feira feira;

	private Estande estande;

	@Autowired
	private AvaliacaoDao dao;

	@Autowired
	private EstandeDao estandeDao;

	@Autowired
	private FeiraService feiraService;

	@BeforeEach
	public void iniciar() throws IOException {
		usuario = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
		Feira feira = FeiraTest.criarFeiraDtoSemUsuario();
		feira.setUsuario(usuario);
		feira = feiraService.cadastrarFeira(feira);
		estande = estandeDao.save(EstandeTest.criarEstande());
	}

	@Test
	@Transactional
	public void testCadastrarAvaliacao() throws IOException, Exception {
		Avaliacao avaliacao = criaAvaliacao();
		MvcResult result = mockMvc.perform(post("/avaliacao").headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
				.andExpect(status().isOk()).andReturn();

		Avaliacao avaliacaoResult = (Avaliacao) TestUtil
				.convertJsonToObject(result.getResponse().getContentAsByteArray(), Avaliacao.class);
		Avaliacao avaliacaoSalva = dao.getOne(avaliacaoResult.getId());
		assertNotNull(avaliacaoSalva);
		assertEquals(avaliacao.getNota(), avaliacaoSalva.getNota());
	}

	@Test
	@Transactional
	public void testAtualizarAvaliacao() throws IOException, Exception {
		Avaliacao avaliacao = criaAvaliacao();
		Avaliacao avaliacaoCadastrada = dao.save(avaliacao);
		avaliacao.setId(avaliacaoCadastrada.getId());
		avaliacao.setNota(7.0);
		avaliacao.setUsuario(usuario);

		mockMvc.perform(put("/avaliacao/" + avaliacaoCadastrada.getId()).headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
				.andExpect(status().isOk());

		Avaliacao avaliacaoSalva = dao.getOne(avaliacaoCadastrada.getId());
		assertEquals(avaliacao.getNota(), avaliacaoSalva.getNota());

	}

	@Test
	@Transactional
	public void testExcluirAvaliacao() throws Exception {
		Avaliacao avaliacao = criaAvaliacao();
		Avaliacao avaliacaoCadastrada = dao.save(avaliacao);

		mockMvc.perform(delete("/avaliacao/" + avaliacaoCadastrada.getId()).headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		assertFalse(dao.findById(avaliacaoCadastrada.getId()).isPresent());
	}

	@Test
	public void testListarTodos() throws Exception {
		Avaliacao avaliacao = criaAvaliacao();
		avaliacao.setNota(7.0);
		dao.saveAndFlush(avaliacao);
		mockMvc.perform(get("/avaliacao").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nota").value(hasItem(avaliacao.getNota())));
	}

	@Test
	@Transactional
	public void testPesquisarPorId() throws Exception {
		Avaliacao avaliacao = criaAvaliacao();
		avaliacao.setNota(7.0);
		Avaliacao avaliacaoCadastrada = dao.saveAndFlush(avaliacao);
		mockMvc.perform(get("/avaliacao/" + avaliacaoCadastrada.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nota").value(avaliacao.getNota()));
	}

	private Avaliacao criaAvaliacao() {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setFeira(feira);
		avaliacao.setEstande(estande);
		avaliacao.setUsuario(usuario);
		avaliacao.setNota(4.5);
		return avaliacao;
	}
}
