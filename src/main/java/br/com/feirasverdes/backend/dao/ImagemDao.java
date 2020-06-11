package br.com.feirasverdes.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.feirasverdes.backend.entidade.Imagem;

public interface ImagemDao extends JpaRepository<Imagem, Long> {

}
