package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.feirasverdes.backend.entidade.Noticia;

public interface NoticiaDao extends JpaRepository<Noticia, Long> {
	
	@Query("select n from Noticia n where n.feira.id = ?1 order by id desc")
	public List<Noticia> buscarUltimasNoticiadaFeira(Long idfeira);

}
