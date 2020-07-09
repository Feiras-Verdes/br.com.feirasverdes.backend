package br.com.feirasverdes.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.feirasverdes.backend.dao.EstandeDao;
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
		estande1.setNome("zzzz");
		Estande estande2 = criarEstande();
		estande2.setNome("zzza");
		estandeDao.save(estande1);
		estandeDao.save(estande2);
		mockMvc.perform(get("/busca/estandes?nome=zzz&limite=10&pagina=0&ordenacao=nome&tipoOrdenacao=asc")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].nome").value("zzza"))
				.andExpect(jsonPath("$.content[1].nome").value("zzzz"));
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
}
