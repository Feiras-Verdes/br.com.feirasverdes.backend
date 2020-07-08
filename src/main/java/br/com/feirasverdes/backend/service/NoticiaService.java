package br.com.feirasverdes.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Noticia;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaDao dao;

	public void atualizarNoticia(final Long id, NoticiaDto noticiaAtualizada) throws IOException {

		Noticia noticia = dao.getOne(id);

		if (noticiaAtualizada.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = noticiaAtualizada.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());

			noticia.setImagem(imagem);
		}
		noticia.setTitulo(noticiaAtualizada.getTitulo());
		noticia.setDescricao(noticiaAtualizada.getTitulo());
		
		dao.save(noticia);
	}

}
