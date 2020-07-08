package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Noticia;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaDao dao;

	@Autowired
	private EstandeDao estandeDao;

	@Autowired
	private FeiraDao feiraDao;

	public Noticia salvar(NoticiaDto noticiaDto) throws IOException {
		Noticia noticia = new Noticia();
		paraNoticia(noticiaDto, noticia);
		return dao.save(noticia);
	}

	public void paraNoticia(NoticiaDto noticiaDto, Noticia noticia) throws IOException {
		noticia.setTitulo(noticiaDto.getTitulo());
		noticia.setDescricao(noticiaDto.getDescricao());
		noticia.setDataPublicacao(LocalDateTime.now());

		if (noticiaDto.getIdEstande() != null) {
			Estande estande = estandeDao.getOne(noticiaDto.getIdEstande());
			noticia.setEstande(estande);
		}

		if (noticiaDto.getIdFeira() != null) {
			Feira feira = feiraDao.getOne(noticiaDto.getIdFeira());
			noticia.setFeira(feira);
		}

		if (noticiaDto.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = noticiaDto.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());
			noticia.setImagem(imagem);
		}
	}

	public void atualizarNoticia(final Long id, NoticiaDto noticiaAtualizada) throws IOException {
		// TODO verificar se usuário chamando método foi quem criou

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

	public List<Noticia> buscarUltimasNoticiadaEstande(Long idEstande) {
		return dao.buscarUltimasNoticiadaEstande(idEstande);
	}

	public List<Noticia> buscarUltimasNoticiadaFeira(Long idFeira) {
		return dao.buscarUltimasNoticiadaFeira(idFeira);
	}

	public void excluirNoticia(Long id) {
		// TODO verificar se usuário chamando método foi quem criou
		dao.deleteById(id);
	}

	public List<Noticia> listarTodos() {
		return dao.findAll();
	}

	public Noticia pesquisarPorId(Long id) {
		return dao.getOne(id);
	}

	public List<Noticia> buscarUltimasNoticias() {
		return dao.buscarUltimasNoticias();
	}

}
