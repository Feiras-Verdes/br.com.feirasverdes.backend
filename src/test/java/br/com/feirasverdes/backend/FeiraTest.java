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

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.service.FeiraService;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class FeiraTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioTestUtil usuarioTestUtil;

	@Autowired
	private FeiraDao feiraDao;

	@Autowired
	private AvaliacaoDao avaliacaoDao;

	@Autowired
	private FeiraService feiraService;

	@Autowired
	private EstandeDao estandeDao;

	@Autowired
	private NoticiaDao noticiaDao;

	private Usuario usuario;

	@BeforeEach
	public void iniciar() {
		usuario = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 3L);
	}

	@Test
	@Transactional
	public void testCadastrarFeira() throws IOException, Exception {
		Feira feira = criarFeira();
		MvcResult result = mockMvc
				.perform(post("/feiras").headers(TestUtil.autHeaders())
						.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feira)))
				.andExpect(status().isOk()).andReturn();
		Feira feiraResult = (Feira) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(),
				Feira.class);

		Feira feiraSalva = feiraDao.getOne(feiraResult.getId());
		assertNotNull(feiraSalva);
		assertEquals(feira.getNome(), feiraSalva.getNome());
		assertEquals(feira.getTelefone(), feiraSalva.getTelefone());
		assertEquals(feira.getFrequencia(), feiraSalva.getFrequencia());
		assertEquals(feira.getHoraInicio(), feiraSalva.getHoraInicio());
		assertEquals(feira.getHoraFim(), feiraSalva.getHoraFim());
		assertEquals(feira.getEndereco(), feiraSalva.getEndereco());
	}

	@Test
	@Transactional
	public void testCadastrarFeiraErro() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		feira.setNome(null);
		mockMvc.perform(post("/feiras").headers(TestUtil.autHeaders()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(feira))).andExpect(status().is(400));
	}

	@Test
	@Transactional
	public void testAtualizarFeira() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeira());
		feira.setId(feiraCadastrada.getId());
		feira.setNome("Feira 2");
		feira.setTelefone("(11) 1111-11111");

		mockMvc.perform(put("/feiras/" + feira.getId()).headers(TestUtil.autHeaders())
				.param("nome", feira.getNome()).param("telefone", feira.getTelefone())
				.param("frequencia", feira.getFrequencia()).param("horaFim", feira.getHoraFim())
				.param("horaInicio", feira.getHoraInicio())
				.param("idUsuario", feiraCadastrada.getUsuario().getId().toString()))
		.andExpect(status().isOk());

		Feira feiraSalva = feiraDao.getOne(feiraCadastrada.getId());
		assertEquals(feira.getNome(), feiraSalva.getNome());
		assertEquals(feira.getTelefone(), feiraSalva.getTelefone());
	}

	@Test
	@Transactional
	public void testExcluirFeira() throws IOException, Exception {
		Feira feira = criarFeira();
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);

		mockMvc.perform(delete("/feiras/" + feiraCadastrada.getId()).headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertFalse(feiraDao.findById(feiraCadastrada.getId()).isPresent());
	}

	@Test
	public void testListarTodos() throws Exception {
		Feira feira = criarFeira();
		feira.setNome("AAA");
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feiras/listarTodos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(feiraCadastrada.getNome())));
	}

	@Test
	public void testListarPorNome() throws Exception {
		Feira feira = criarFeira();
		feira.setNome("ZZZ");
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feiras/pesquisar-por-nome/z").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[*].nome").value(hasItem(feiraCadastrada.getNome())));
	}

	@Test
	public void testPesquisarPorId() throws Exception {
		Feira feira = criarFeira();
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feiras/" + feiraCadastrada.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value(feiraCadastrada.getNome()));
	}

	@Test
	@Transactional
	public void testCadastrarAvaliacaoFeira() throws IOException, Exception {
		Feira feira = feiraService.cadastrarFeira(criarFeira());
		Avaliacao avaliacao = criaAvaliacao(feira);
		MvcResult result = mockMvc
				.perform(post("/feiras/"+feira.getId()+"/avaliar").headers(TestUtil.autHeaders())
						.contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(avaliacao)))
				.andExpect(status().isOk()).andReturn();
		Avaliacao avaliacaoResult = (Avaliacao) TestUtil
				.convertJsonToObject(result.getResponse().getContentAsByteArray(), Avaliacao.class);
		Avaliacao avaliacaoSalva = avaliacaoDao.getOne(avaliacaoResult.getId());
		assertNotNull(avaliacaoSalva);
		assertEquals(feira.getId(), avaliacaoSalva.getFeira().getId());
		assertEquals(usuario.getId(), avaliacaoSalva.getUsuario().getId());
		assertEquals(avaliacao.getNota(), avaliacaoSalva.getNota());
	}

	@Test
	public void testListarAvaliacaoUsuario() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeira());
		avaliacaoDao.save(criaAvaliacao(feiraCadastrada));
		mockMvc.perform(get("/feiras/listarAvaliacaoUsuario/" + usuario.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[*].nota").value(hasItem(6d)));
	}

	@Test
	public void testPesquisarPortodosEstandesdaFeira() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeira());

		Estande estande = new Estande();
		estande.setFeira(feiraCadastrada);
		estande.setUsuario(usuario);
		estande.setFrequencia("Todos os dias");
		estande.setHora_inicio("10:00");
		estande.setHora_fim("17:00");
		estande.setNome("Estande 1");
		estande.setTelefone("(00) 0000-00000");
		estandeDao.save(estande);

		mockMvc.perform(get("/feiras/" + feiraCadastrada.getId() + "/pesquisar-por-todas-estandes-da-feira/es")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(estande.getNome())));
	}

	@Test
	public void testPesquisarPorMelhoresFeiras() throws Exception {
		Feira feira1 = feiraService.cadastrarFeira(criarFeira());
		avaliacaoDao.save(criaAvaliacao(feira1, 6d));
		avaliacaoDao.save(criaAvaliacao(feira1, 8d));

		Feira feira2 = criarFeira();
		feira2.setNome("Feira 2");
		feiraService.cadastrarFeira(feira2);
		avaliacaoDao.save(criaAvaliacao(feira2, 9d));
		avaliacaoDao.save(criaAvaliacao(feira2, 7d));

		mockMvc.perform(get("/feiras/pesquisar-melhores-feiras").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[0].nome").value(feira2.getNome()))
				.andExpect(jsonPath("$.[1].nome").value(feira1.getNome()));
	}

	@Test
	public void testPesquisarPorNoticiasdasFeiras() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeira());
		Noticia noticia = new Noticia();
		noticia.setDescricao("Descrição noticia 1");
		noticia.setFeira(feiraCadastrada);
		noticia.setTitulo("Noticia 1");
		noticia.setDataPublicacao(LocalDateTime.now());
		noticiaDao.save(noticia);

		mockMvc.perform(get("/feiras/" + feiraCadastrada.getId()+"/noticias")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].titulo").value(hasItem(noticia.getTitulo())));
	}

	public FeiraDto criarFeiraDto() {
		FeiraDto feira = criarFeiraDtoSemUsuarioDto();
		feira.setIdUsuario(usuario.getId());
		return feira;
	}

	public static FeiraDto criarFeiraDtoSemUsuarioDto() {
		FeiraDto feira = new FeiraDto();
		feira.setLogradouro("Rua 1");
		feira.setBairro("Bairro 1");
		feira.setCep("00000-000");
		feira.setCidade("Cidade 1");
		feira.setEstado("SC");
		feira.setNumero(1);

		feira.setFrequencia("Todos os dias");
		feira.setNome("Feira");
		feira.setTelefone("(00) 0000-0000");
		feira.setHoraInicio("10:00");
		feira.setHoraFim("18:00");
		return feira;
	}

	public static Feira criarFeiraDtoSemUsuario() {
		Feira feira = new Feira();
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua 1");
		endereco.setBairro("Bairro 1");
		endereco.setCep("00000-000");
		endereco.setCidade("Cidade 1");
		endereco.setEstado("SC");
		endereco.setNumero(1);
		feira.setEndereco(endereco);

		feira.setFrequencia("Todos os dias");
		feira.setNome("Feira");
		feira.setTelefone("(00) 0000-0000");
		feira.setHoraInicio("10:00");
		feira.setHoraFim("18:00");
		return feira;
	}

	private Feira criarFeira() {
		Feira feira = new Feira();
		feira.setNome("Feira");
		feira.setUsuario(usuario);

		return feira;
	}

	private Avaliacao criaAvaliacao(Feira feiraCadastrada) {
		return criaAvaliacao(feiraCadastrada, 6d);
	}

	private Avaliacao criaAvaliacao(Feira feiraCadastrada, Double nota) {
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setFeira(feiraCadastrada);
		avaliacao.setUsuario(usuario);
		avaliacao.setNota(nota);
		return avaliacao;
	}

}
