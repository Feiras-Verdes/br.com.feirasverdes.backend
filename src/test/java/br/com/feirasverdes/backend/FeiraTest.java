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
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.EnderecoDto;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.entidade.Avaliacao;
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
		usuario = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
	}
	
	@Test
	@Transactional
	public void testCadastrarFeira() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		MvcResult result = mockMvc.perform(post("/feira/cadastrar")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(feira)))
				.andExpect(status().isOk())
				.andReturn();
		Feira feiraResult = (Feira) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(), Feira.class);
		
		Feira feiraSalva = feiraDao.getOne(feiraResult.getId());
		assertNotNull(feiraSalva);
		assertEquals(feira.getNome(), feiraSalva.getNome());
		assertEquals(feira.getTelefone(), feiraSalva.getTelefone());
		assertEquals(feira.getFrequencia(), feiraSalva.getFrequencia());
		assertEquals(feira.getHora_inicio(), feiraSalva.getHora_inicio());
		assertEquals(feira.getHora_fim(), feiraSalva.getHora_fim());
		assertEquals(feira.getEndereco().getCep(), feiraSalva.getEndereco().getCep());
		assertEquals(feira.getEndereco().getBairro(), feiraSalva.getEndereco().getBairro());
		assertEquals(feira.getEndereco().getCidade(), feiraSalva.getEndereco().getCidade());
		assertEquals(feira.getEndereco().getComplemento(), feiraSalva.getEndereco().getComplemento());
		assertEquals(feira.getEndereco().getEstado(), feiraSalva.getEndereco().getEstado());
		assertEquals(feira.getEndereco().getNumero(), feiraSalva.getEndereco().getNumero());
		assertEquals(feira.getEndereco().getRua(), feiraSalva.getEndereco().getRua());
		
	}
	
	@Test
	@Transactional
	public void testCadastrarFeiraErro() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		feira.setNome(null);
		mockMvc.perform(post("/feira/cadastrar")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(feira)))
				.andExpect(status().is(400));
	}
	
	@Test
	@Transactional
	public void testAtualizarFeira() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		feira.setId(feiraCadastrada.getId());
		feira.setNome("Feira 2");
		feira.setTelefone("(11) 1111-11111");
		
		mockMvc.perform(put("/feira/"+feira.getId()+"/atualizar")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(feira)))
				.andExpect(status().isOk());
		
		Feira feiraSalva = feiraDao.getOne(feiraCadastrada.getId());
		assertEquals(feira.getNome(), feiraSalva.getNome());
		assertEquals(feira.getTelefone(), feiraSalva.getTelefone());
	}
	
	@Test
	@Transactional
	public void testExcluirFeira() throws IOException, Exception {
		FeiraDto feira = criarFeiraDto();
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		
		mockMvc.perform(delete("/feira/"+feiraCadastrada.getId()+"/excluir")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertFalse(feiraDao.findById(feiraCadastrada.getId()).isPresent());
	}

	@Test
	public void testListarTodos() throws Exception {
		FeiraDto feira = criarFeiraDto();
		feira.setNome("AAA");
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feira/listarTodos").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].nome").value(hasItem(feiraCadastrada.getNome())));
	}
	
	@Test
	public void testListarPorNome() throws Exception {
		FeiraDto feira = criarFeiraDto();
		feira.setNome("ZZZ");
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feira/pesquisar-por-nome/z").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].nome").value(hasItem(feiraCadastrada.getNome())));
	}
	
	@Test
	public void testPesquisarPorId() throws Exception {
		FeiraDto feira = criarFeiraDto();
		Feira feiraCadastrada = feiraService.cadastrarFeira(feira);
		mockMvc.perform(get("/feira/pesquisar-por-id/"+feiraCadastrada.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.nome").value(feiraCadastrada.getNome()));
	}
	
	@Test
	@Transactional
	public void testCadastrarAvaliacaoFeira() throws IOException, Exception {
		Feira feira = feiraService.cadastrarFeira(criarFeiraDto());
		Avaliacao avaliacao = criaAvaliacao(feira);
		MvcResult result = mockMvc.perform(post("/feira/cadastrarAvaliacaoFeira")
				.headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(avaliacao)))
				.andExpect(status().isOk())
				.andReturn();
		Avaliacao avaliacaoResult = (Avaliacao) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(), Avaliacao.class);
		Avaliacao avaliacaoSalva = avaliacaoDao.getOne(avaliacaoResult.getId());
		assertNotNull(avaliacaoSalva);
		assertEquals(feira.getId(), avaliacaoSalva.getFeira().getId());
		assertEquals(usuario.getId(), avaliacaoSalva.getUsuario().getId());
		assertEquals(avaliacao.getNota(), avaliacaoSalva.getNota());
	}
	
	@Test
	public void testListarAvaliacaoUsuario() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeiraDto());
		avaliacaoDao.save(criaAvaliacao(feiraCadastrada));
		mockMvc.perform(get("/feira/listarAvaliacaoUsuario/"+usuario.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].nota").value(hasItem(6d)));
	}
	
	@Test
	public void testPesquisarPortodosEstandesdaFeira() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeiraDto());
		
		Estande estande = new Estande();
		estande.setFeira(feiraCadastrada);
		estande.setUsuario(usuario);
		estande.setFrequencia("Todos os dias");
		estande.setHora_inicio("10:00");
		estande.setHora_fim("17:00");
		estande.setNome("Estande 1");
		estande.setTelefone("(00) 0000-00000");
		estandeDao.save(estande);
		
		mockMvc.perform(get("/feira/"+feiraCadastrada.getId()+"/pesquisar-por-todas-estandes-da-feira/es").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].nome").value(hasItem(estande.getNome())));
	}
	
	@Test
	public void testPesquisarPorMelhoresFeiras() throws Exception {
		Feira feira1 = feiraService.cadastrarFeira(criarFeiraDto());
		avaliacaoDao.save(criaAvaliacao(feira1, 6d));
		avaliacaoDao.save(criaAvaliacao(feira1, 8d));
		
		
		FeiraDto feiraDto2 = criarFeiraDto();
		feiraDto2.setNome("Feira 2");
		Feira feira2 = feiraService.cadastrarFeira(feiraDto2);
		avaliacaoDao.save(criaAvaliacao(feira2, 9d));
		avaliacaoDao.save(criaAvaliacao(feira2, 7d));
		
		mockMvc.perform(get("/feira/pesquisar-melhores-feiras").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].nome").value(feira2.getNome()))
			.andExpect(jsonPath("$.[1].nome").value(feira1.getNome()));
	}
	
	
	@Test
	public void testPesquisarPorNoticiasdasFeiras() throws Exception {
		Feira feiraCadastrada = feiraService.cadastrarFeira(criarFeiraDto());
		Noticia noticia = new Noticia();
		noticia.setDescricao("Descrição noticia 1");
		noticia.setFeira(feiraCadastrada);
		noticia.setTitulo("Noticia 1");
		noticiaDao.save(noticia);
		
		mockMvc.perform(get("/feira/pesquisar-por-noticias-feiras/"+feiraCadastrada.getId()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].titulo").value(hasItem(noticia.getTitulo())));
	}

	private FeiraDto criarFeiraDto() {
		FeiraDto feira = new FeiraDto();
		EnderecoDto endereco = new EnderecoDto();
		endereco.setRua("Rua 1");
		endereco.setBairro("Bairro 1");
		endereco.setCep("00000-000");
		endereco.setCidade("Cidade 1");
		endereco.setEstado("SC");
		endereco.setNumero(1);
		feira.setEndereco(endereco);
		
		feira.setFrequencia("Todos os dias");
		feira.setNome("Feira");
		feira.setTelefone("(00) 0000-0000");
		feira.setHora_inicio("10:00");
		feira.setHora_fim("18:00");
		feira.setIdUsuario(usuario.getId());
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
