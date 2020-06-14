package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Estande;

@Repository
public interface EstandeDao extends JpaRepository<Estande, Long> {
	@Transactional
	@Modifying
	@Query(value = "select * from Estande u where nome like '%?1%'", nativeQuery = true)
	List<Estande> pesquisarPorNome(String nome);
	
	@Query(value = "select e from Estande e where e.feira.id = ?1")
	List<Estande> pesquisarPortodosEstandesdaFeira(Long idFeira);
}