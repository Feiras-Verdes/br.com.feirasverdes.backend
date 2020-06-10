package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Usuario;

@Repository
public interface FeiranteDao extends JpaRepository<Usuario, Long> {
	@Transactional
	@Modifying
	@Query(value = "select * from usuario where nome like '%?3%'", nativeQuery = true)
	List<Usuario> pesquisarPorNome(String nome);

}
