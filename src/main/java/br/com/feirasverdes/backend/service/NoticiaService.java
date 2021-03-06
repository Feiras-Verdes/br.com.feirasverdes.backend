package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.NoticiaDetalhesDto;
import br.com.feirasverdes.backend.dto.NoticiaDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Noticia;
import br.com.feirasverdes.backend.exception.AvaliacaoNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.exception.NoticiaNaoPertenceAoUsuarioException;

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

	public void atualizarNoticia(final Long id, NoticiaDto noticiaAtualizada)
			throws IOException, NoticiaNaoPertenceAoUsuarioException {
		Noticia noticia = dao.getOne(id);

		/*
		 * if (noticiaAtualizada.getIdEstande() != null) { Estande estande =
		 * estandeDao.getOne(noticiaAtualizada.getIdEstande());
		 * if(!estande.getUsuario().getId().equals(noticia.getId())) { throw new
		 * NoticiaNaoPertenceAoUsuarioException("Esta notícia não foi cadastrada por você."
		 * ); } }
		 * 
		 * if (noticiaAtualizada.getIdFeira() != null) { Feira feira =
		 * feiraDao.getOne(noticiaAtualizada.getIdFeira());
		 * if(!feira.getUsuario().getId().equals(noticia.getId())) { throw new
		 * NoticiaNaoPertenceAoUsuarioException("Esta notícia não foi cadastrada por você."
		 * ); } }
		 */

		if (noticiaAtualizada.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = noticiaAtualizada.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());

			noticia.setImagem(imagem);
		}
		noticia.setTitulo(noticiaAtualizada.getTitulo());
		noticia.setDescricao(noticiaAtualizada.getDescricao());

		dao.save(noticia);
	}

	public List<NoticiaDetalhesDto> buscarUltimasNoticiadaEstande(Long idEstande) {
		List<Noticia> noticias = dao.buscarUltimasNoticiadaEstande(idEstande);
		List<NoticiaDetalhesDto> noticiasDto = new ArrayList<NoticiaDetalhesDto>();

		for (Noticia noticia : noticias) {
			noticiasDto.add(paraNoticiaDetalhesDto(noticia));
		}
		return noticiasDto;
	}

	private NoticiaDetalhesDto paraNoticiaDetalhesDto(Noticia noticia) {
		NoticiaDetalhesDto noticiaDto = new NoticiaDetalhesDto();
		noticiaDto.setId(noticia.getId());
		noticiaDto.setTitulo(noticia.getTitulo());
		noticiaDto.setDescricao(noticia.getDescricao());
		noticiaDto.setFeira(noticia.getFeira());
		noticiaDto.setEstande(noticia.getEstande());
		noticiaDto.setDataPublicacao(
				noticia.getDataPublicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
		noticiaDto.setImagem(noticia.getImagem());

		return noticiaDto;
	}

	public List<NoticiaDetalhesDto> buscarUltimasNoticiadaFeira(Long idFeira) {
		List<Noticia> noticias = dao.buscarUltimasNoticiadaFeira(idFeira);
		List<NoticiaDetalhesDto> noticiasDto = new ArrayList<NoticiaDetalhesDto>();

		for (Noticia noticia : noticias) {
			noticiasDto.add(paraNoticiaDetalhesDto(noticia));
		}
		return noticiasDto;
	}

	public void excluirNoticia(Long id) throws NoticiaNaoPertenceAoUsuarioException {
		Noticia noticia = dao.getOne(id);
		/*
		 * if (noticia.getEstande().getId() != null) { Estande estande =
		 * estandeDao.getOne(noticia.getEstande().getId());
		 * if(!estande.getId().equals(noticia.getEstande().getId())) { throw new
		 * NoticiaNaoPertenceAoUsuarioException("Esta notícia não foi cadastrada por você."
		 * ); } }
		 * 
		 * if (noticia.getFeira().getId() != null) { Feira feira =
		 * feiraDao.getOne(noticia.getFeira().getId());
		 * if(!feira.getId().equals(noticia.getFeira().getId())) { throw new
		 * NoticiaNaoPertenceAoUsuarioException("Esta notícia não foi cadastrada por você."
		 * ); } }
		 */

		dao.deleteById(id);
	}

	public List<NoticiaDetalhesDto> listarTodos() {
		List<Noticia> noticias = dao.findAll();
		List<NoticiaDetalhesDto> noticiasDto = new ArrayList<NoticiaDetalhesDto>();

		for (Noticia noticia : noticias) {
			noticiasDto.add(paraNoticiaDetalhesDto(noticia));
		}
		return noticiasDto;
	}

	public NoticiaDetalhesDto pesquisarPorId(Long id) {
		Noticia noticia = dao.getOne(id);
		return paraNoticiaDetalhesDto(noticia);
	}

	public List<NoticiaDetalhesDto> buscarUltimasNoticias() {
		List<Noticia> noticias = dao.buscarUltimasNoticias();
		List<NoticiaDetalhesDto> noticiasDto = new ArrayList<NoticiaDetalhesDto>();

		for (Noticia noticia : noticias) {
			noticiasDto.add(paraNoticiaDetalhesDto(noticia));
		}
		return noticiasDto;
	}

}
