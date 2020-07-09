package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.dto.EstandeDetalheDto;
import br.com.feirasverdes.backend.entidade.Estande;

@Repository
public interface EstandeDao extends JpaRepository<Estande, Long> {

	@Query(value = "select e from Estande e where e.nome like %?1%")
	List<Estande> pesquisarPorNome(String nome);

	@Query(value = "select e from Estande e where e.feira.id = ?1 and upper(e.nome) like ?2")
	List<Estande> pesquisarPorFeiraENome(Long idFeira, String nome);

	@Query(value = "select e from Estande e where e.feira.id = ?1")
	List<Estande> pesquisarPortodosEstandesdaFeira(Long idFeira);

	@Query(value = "select new br.com.feirasverdes.backend.dto.EstabelecimentoDto"
			+ "(e.id, e.nome, e.telefone, imagem, endereco , (CEILING(AVG(a.nota) / 0.5) * 0.5))" + " from Estande e "
			+ " left join e.endereco endereco" + " left join e.imagem imagem" + " left join e.avaliacoes a "
			+ " where upper(e.nome) like ?1 " + " group by e.nome, e.id, e.telefone, a.nota")
	Page<EstabelecimentoDto> buscaEstandePorFiltro(String nome, Pageable pageable);
	

	List<Estande> findByUsuarioId(Long usuarioId);

	
	//Long id, String horaInicio, String frequencia, String horaFim, String telefone, String nome, Feira feira, Endereco endereco, Usuario usuario, Imagem imagem, Double avaliacao
	@Query(value = "select new br.com.feirasverdes.backend.dto.EstandeDetalheDto"
			+ "(e.id, e.horaInicio, e.frequencia, e.horaFim, e.telefone, e.nome, feira, endereco, usuario, imagem, (CEILING(AVG(a.nota) / 0.5) * 0.5))" 
			+ " from Estande e "
			+ " join e.feira feira "
			+ " left join e.endereco endereco " 
			+ " left join e.imagem imagem " 
			+ " left join e.avaliacoes a "
			+ " left join e.usuario usuario "
			+ " where feira.id = ?1 "
			+ " group by e.id, e.horaInicio, e.frequencia, e.horaFim, e.telefone, e.nome, feira, endereco, usuario, imagem ")
	List<EstandeDetalheDto> findByFeiraId(Long idFeira);
	
	@Query(value = "select COALESCE(avg(avaliacao.nota), 0) from Avaliacao avaliacao right join avaliacao.estande estande where estande.id = ?1")
	Number avaliacaoPorEstande(Long id);

}