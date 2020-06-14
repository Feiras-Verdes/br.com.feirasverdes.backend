package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Feira;

public interface FeiraDao extends JpaRepository<Feira, Long> {

	@Transactional
	@Modifying
	@Query(value = "select * from produto u where nome like '%?1%'", nativeQuery = true)
	List<Feira> pesquisarPorNome(String nome);
	
	//, avg( avaliacao.nota), count(avaliacao.id)
	@Query("select distinct feira from Avaliacao avaliacao join avaliacao.feira feira order by avg( avaliacao.nota) desc")
	public List<Feira> buscarMelhoresFeiras();
}
