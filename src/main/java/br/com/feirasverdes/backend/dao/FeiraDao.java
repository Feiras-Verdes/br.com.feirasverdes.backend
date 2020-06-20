package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.feirasverdes.backend.entidade.Feira;

public interface FeiraDao extends JpaRepository<Feira, Long> {

	@Query(value = "from Feira where nome like ?1")
	List<Feira> pesquisarPorNome(String nome);
	
	//, avg( avaliacao.nota), count(avaliacao.id)
	@Query("select feira from Avaliacao avaliacao right join avaliacao.feira feira group by feira order by avg(avaliacao.nota) desc")
	public List<Feira> buscarMelhoresFeiras();
	
	@Query(value = "select f from Feira f where upper(f.nome) like ?1 ")
	Page<Feira> buscaEstandePorFiltro(String nome, Pageable pageable);
}
