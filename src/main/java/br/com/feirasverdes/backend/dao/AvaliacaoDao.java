package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.feirasverdes.backend.entidade.Avaliacao;

public interface AvaliacaoDao extends JpaRepository<Avaliacao, Long>{

	@Query("select a from Avaliacao a where a.usuario.id = ?1")
	public List<Avaliacao> findByUsuarioId(Long usuarioId);
	
	
	
}