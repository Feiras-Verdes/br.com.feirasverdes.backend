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
public class BuscaTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private EstandeDao estandeDao;
	
	
	@Autowired
	private UsuarioTestUtil usuarioTestUtil;
	
	@BeforeEach
	public void iniciar() {
		usuarioTestUtil.criarUsuarioLogin("test@localhost", "123456", 1L);
	}
	
	@Test
	public void testBuscaEstabelecimento() throws Exception {
		Estande estande1 = criarEstande();
		estande1.setNome("ab");
		Estande estande2 = criarEstande();
		estande2.setNome("aa");
		estandeDao.save(estande1);
		estandeDao.save(estande2);
		mockMvc.perform(get("/busca/estabelecimentos?nome=a&limite=10&pagina=0&ordenacao=nome&tipoOrdenacao=asc").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.content[0].nome").value("aa"))
		.andExpect(jsonPath("$.content[1].nome").value("ab"));
	}
	
	public static Estande criarEstande() {
		Estande estande = new Estande();
		estande.setNome("barraca1");
		estande.setTelefone("(00) 0000-0000");
		estande.setFrequencia("segunda á sexta");
		estande.setHora_inicio("09:00");
		estande.setHora_fim("18:00");
		return estande;
		
	}
}
