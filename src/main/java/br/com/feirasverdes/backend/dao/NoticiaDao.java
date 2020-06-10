package br.com.feirasverdes.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.feirasverdes.backend.entidade.Noticia;

public interface NoticiaDao extends JpaRepository<Noticia, Long> {

}
