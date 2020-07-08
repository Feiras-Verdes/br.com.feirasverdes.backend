package br.com.feirasverdes.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.entidade.Noticia;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaDao dao;

	public void atualizarNoticia(final Long id, NoticiaDto noticiaAtualizada) throws IOException {

		Noticia noticia = dao.getOne(id);

		noticia.setImagem(noticiaAtualizada.getImagem());
		noticia.setTitulo(noticiaAtualizada.getTitulo());
		noticia.setDescricao(noticiaAtualizada.getTitulo());
		
		dao.save(noticia);
	}

}
