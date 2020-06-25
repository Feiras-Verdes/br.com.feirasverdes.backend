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
	
//	@Query(value = "select e.id, e.nome, e.endereco, e.telefone, a.nota from Estande e " +
//			       " join e.avaliacoes a "  +
//			       "where upper(e.nome) like ?1 ")
//	Page<Estande> buscaEstandePorFiltro(String nome, Pageable pageable);
	
	@Query(value = "select new br.com.feirasverdes.backend.dto.EstabelecimentoDto"
			 + "(e.id, e.nome, e.telefone, e.endereco, avg(a.nota)) "
			 + " from Estande e " +
		       " join e.avaliacoes a "  +
		       "where upper(e.nome) like ?1 ")
    Page<EstabelecimentoDto> buscaEstandePorFiltro(String nome, Pageable pageable);
	
	// select e.nome from Estande e join Avaliacao a on e.id = a.id_estande  join Endereco c on join Endereco c on where upper(e.nome) like ?1 
	
}