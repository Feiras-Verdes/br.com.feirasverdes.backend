package br.com.feirasverdes.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.feirasverdes.backend.entidade.Avaliacao;

public interface AvaliacaoDao extends JpaRepository<Avaliacao, Long>{

}
