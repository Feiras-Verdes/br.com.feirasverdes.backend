package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Produto;

@Repository
public interface ProdutoDao extends JpaRepository<Produto, Long> {
	
	@Query(value = "select u from Produto u where nome like %?1%")
	List<Produto> pesquisarPorNome(String nome);

}