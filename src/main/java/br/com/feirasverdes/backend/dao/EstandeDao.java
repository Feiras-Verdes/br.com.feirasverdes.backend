package br.com.feirasverdes.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.entidade.Estande;

@Repository
public interface EstandeDao extends JpaRepository<Estande, Long> {
	
	@Query(value = "select e from Estande e where e.nome like ?1")
	List<Estande> pesquisarPorNome(String nome);
	
	@Query(value = "select e from Estande e where e.feira.id = ?1 and upper(e.nome) like ?2")
	List<Estande> pesquisarPorFeiraENome(Long idFeira, String nome);
	
	@Query(value = "select e from Estande e where e.feira.id = ?1") 
	List<Estande> pesquisarPortodosEstandesdaFeira(Long idFeira);
	
	@Query(value = "select new br.com.feirasverdes.backend.dto.EstabelecimentoDto"
			 + "(e.id, e.nome, e.telefone, e.imagem, e.endereco , COALESCE(avg(a.nota), 0)) "
			 + " from Estande e " +
		       " left join e.avaliacoes a "  +
		       "where upper(e.nome) like ?1 group by e.nome, e.id, e.telefone, e.endereco")
    Page<EstabelecimentoDto> buscaEstandePorFiltro(String nome, Pageable pageable);
	
}