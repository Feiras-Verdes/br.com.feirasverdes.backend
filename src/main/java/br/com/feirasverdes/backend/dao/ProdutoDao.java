package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.feirasverdes.backend.entidade.Produto;

@Repository
public interface ProdutoDao extends JpaRepository<Produto, Long> {

	@Query(value = "select p from Produto p where nome like %?1%")
	List<Produto> pesquisarPorNome(String nome);

	@Query(value = "select u from Produto u where upper(u.nome) like ?1 ")
	Page<Produto> buscaProdutoPorFiltro(String nome, Pageable pageable);

	@Query(value = "select p from Produto p join p.estande e where e.id = ?1")
	List<Produto> findByEstandeId(Long idEstande);

}