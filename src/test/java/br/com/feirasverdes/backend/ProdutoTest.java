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
import br.com.feirasverdes.backend.dao.ProdutoDao;
import br.com.feirasverdes.backend.dto.ProdutoDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Produto;
import br.com.feirasverdes.backend.entidade.Usuario;

@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class ProdutoTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProdutoDao produtodao;

	@Autowired
	private UsuarioTestUtil usuarioTestUtil;
	
	private Estande estande;

	@Autowired
	private EstandeDao estandeDao;

	private Usuario usuarioLogado;

	@BeforeEach
	public void iniciar() {
		usuarioLogado = usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 3L);
		Estande criarEstande = EstandeTest.criarEstande();
		criarEstande.setUsuario(usuarioLogado);
		estande = estandeDao.save(criarEstande);
	}

	@Test
	@Transactional
	public void testCadastrarProduto() throws IOException, Exception {
		ProdutoDto produto = criarProdutoDto();
		MvcResult result = mockMvc.perform(post("/produtos").headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)
				.param("nome", produto.getNome())
				.param("descricao", produto.getDescricao())
				.param("preco", produto.getPreco().toString())
				.param("unidade", produto.getUnidade()))
				.andExpect(status().isOk()).andReturn();
		Produto produtoResult = (Produto) TestUtil.convertJsonToObject(result.getResponse().getContentAsByteArray(),
				Produto.class);

		Produto produtoSalvo = produtodao.getOne(produtoResult.getId());
		assertNotNull(produtoSalvo);
		assertEquals(produto.getNome(), produtoSalvo.getNome());
		assertEquals(produto.getPreco(), produtoSalvo.getPreco());
		assertEquals(produto.getDescricao(), produtoSalvo.getDescricao());

	}

	@Test
	public void testAtualizarProduto() throws IOException, Exception {
		Produto produtoCadastrado = cadastrarProduto();
		ProdutoDto produto = criarProdutoDto();
		produto.setId(produtoCadastrado.getId());
		produto.setNome("Laranja lima");
		produto.setPreco(5.00f);

		mockMvc.perform(put("/produtos/" + produto.getId()).headers(TestUtil.autHeaders())
				.param("nome", produto.getNome()).param("descricao", produto.getDescricao())
				.param("preco", produto.getPreco().toString()).param("unidade", produto.getUnidade()))
				.andExpect(status().isOk());

		Produto produtoSalvo = produtodao.findById(produtoCadastrado.getId()).get();
		assertEquals(produto.getNome(), produtoSalvo.getNome());
		assertEquals(produto.getPreco(), produtoSalvo.getPreco());
	}

	@Test
	public void testExcluirProduto() throws IOException, Exception {
		Produto produto = criarProduto();
		Produto produtoCadastrado = produtodao.save(produto);

		mockMvc.perform(delete("/produtos/" + produtoCadastrado.getId()).headers(TestUtil.autHeaders())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertFalse(produtodao.findById(produtoCadastrado.getId()).isPresent());
	}

	@Test
	public void testListarTodos() throws Exception {
		Produto produto = criarProduto();
		Produto produtoCadastrado = produtodao.save(produto);
		mockMvc.perform(get("/produtos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(produtoCadastrado.getNome())));
	}

	@Test
	public void testListarPorNome() throws Exception {
		Produto produto = criarProduto();
		produto.setNome("AA");
		Produto produtoCadastrado = produtodao.save(produto);
		mockMvc.perform(get("/produtos/pesquisar-por-nome?nome=AA").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].nome").value(hasItem(produtoCadastrado.getNome())));
	}

	@Test
	public void testPesquisarPorId() throws Exception {
		Produto produto = criarProduto();
		Produto produtoCadastrado = produtodao.save(produto);
		mockMvc.perform(get("/produtos/" + produtoCadastrado.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value(produtoCadastrado.getNome()));
	}

	@Transactional
	private Produto cadastrarProduto() {
		return produtodao.saveAndFlush(criarProduto());
	}

	private ProdutoDto criarProdutoDto() {
		ProdutoDto produto = new ProdutoDto();
		produto.setNome("Laranja");
		produto.setPreco(3.00f);
		produto.setUnidade("kg");
		produto.setDescricao("do sitio");
		return produto;
	}

	private Produto criarProduto() {
		Produto produto = new Produto();
		produto.setNome("Laranja");
		produto.setPreco(3.00f);
		produto.setDescricao("do sitio");
		produto.setUnidade("kg");
		produto.setEstande(estande);
		return produto;

	}

}
