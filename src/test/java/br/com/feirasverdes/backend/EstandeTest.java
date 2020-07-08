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
import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.entidade.Estande;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class EstandeTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EstandeDao estandeDao;

	@Autowired
	private AvaliacaoDao avaliacaodao;

	@Autowired
	private UsuarioTestUtil usuarioTestUtil;

	@BeforeEach
	public void iniciar() {
		usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
	}

	@Test
	@Transactional
	public void testCadastrarEstande() throws Exception {
		Estande estande = criarEstande();
		MvcResult result = mockMvc.perform(post("/estandes").headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estande)))
				.andExpect(status().isOk()).andReturn();
		Estande estandeResult = (Estande) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(),
				Estande.class);

		Estande estandeSalvo = estandeDao.getOne(estandeResult.getId());
		assertNotNull(estandeSalvo);
		assertEquals(estande.getNome(), estandeSalvo.getNome());
		assertEquals(estande.getTelefone(), estandeSalvo.getTelefone());
		assertEquals(estande.getHoraInicio(), estandeSalvo.getHoraInicio());
		assertEquals(estande.getHoraFim(), estandeSalvo.getHoraFim());
		assertEquals(estande.getFrequencia(), estandeSalvo.getFrequencia());

	}

	@Test
	@Transactional
	public void testAtualizarEstande() throws IOException, Exception {
		Estande estande = criarEstande();
		Estande estandeCadastrado = estandeDao.save(estande);
		estande.setId(estandeCadastrado.getId());
		estande.setNome("barraca2");
		estande.setTelefone("(00) 0000-0000");
		estande.setFrequencia("segunda á sabado");
		estande.setHoraInicio("08:00");
		estande.setHoraFim("17:00");

		mockMvc.perform(
				put("/estandes/" + estande.getId()).headers(TestUtil.autHeaders()).param("nome", estande.getNome())
						.param("telefone", estande.getTelefone()).param("frequencia", estande.getFrequencia())
						.param("horaInicio", estande.getHoraInicio()).param("horaFim", estande.getHoraFim()))
				.andExpect(status().isOk()).andReturn();

		Estande estandeSalvo = estandeDao.getOne(estandeCadastrado.getId());
		assertEquals(estande.getNome(), estandeSalvo.getNome());
		assertEquals(estande.getTelefone(), estandeSalvo.getTelefone());
	}

	@Test
	@Transactional
	public void testExcluirEstande() throws Exception {
		Estande estande = criarEstande();
		Estande estandeCadastrado = estandeDao.save(estande);

		mockMvc.perform(delete("/estandes/" + estandeCadastrado.getId()).headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		assertFalse(estandeDao.findById(estandeCadastrado.getId()).isPresent());

	}

	@Test
	public void testListarTodos() throws Exception {
		Estande estande = criarEstande();
		estande.setNome("b");

		Estande estandecadastrado = estandeDao.save(estande);
		mockMvc.perform(get("/estandes/listarTodos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(estandecadastrado.getNome())));
	}

	@Test
	public void testListarPornome() throws Exception {
		Estande estande = criarEstande();
		estande.setNome("b");
		Estande estandeCadastrado = estandeDao.save(estande);
		mockMvc.perform(get("/estandes/pesquisar-por-nome?nome=b").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(estandeCadastrado.getNome())));
	}

	@Test
	public void testListarPorId() throws Exception {
		Estande estande = criarEstande();
		Estande estandeCadastrado = estandeDao.save(estande);
		mockMvc.perform(get("/estandes/" + estandeCadastrado.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value(estandeCadastrado.getNome()));

	}

	public static Estande criarEstande() {
		Estande estande = new Estande();
		estande.setNome("barraca1");
		estande.setTelefone("(00) 0000-0000");
		estande.setFrequencia("segunda á sexta");
		estande.setHoraInicio("09:00");
		estande.setHoraFim("18:00");
		return estande;

	}

	private EstandeDto criarEstandeDto() {
		EstandeDto estande = new EstandeDto();
		estande.setNome("barraca1");
		estande.setTelefone("(00) 0000-0000");
		estande.setFrequencia("segunda á sexta");
		estande.setHoraInicio("09:00");
		estande.setHoraFim("18:00");
		return estande;

	}

}
