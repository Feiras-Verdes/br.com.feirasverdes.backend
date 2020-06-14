package br.com.feirasverdes.backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Usuario;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

	@Transactional
	@Query(value = "select * from usuario where nome like %?1% and ativo = 1", nativeQuery = true)
	List<Usuario> pesquisarPorNome(String nome);

	@Transactional
	@Query(value = "select * from usuario where ativo = 1", nativeQuery = true)
	List<Usuario> listarTodos();

	@Transactional
	@Query(value = "select * from usuario where id = ?1 and ativo = 1", nativeQuery = true)
	Optional<Usuario> pesquisarPorId(Long id);

	@Transactional
	@Query(value = "select * from usuario where email = ?1 and ativo = 1", nativeQuery = true)
	Usuario pesquisarPorEmail(String email);

}
