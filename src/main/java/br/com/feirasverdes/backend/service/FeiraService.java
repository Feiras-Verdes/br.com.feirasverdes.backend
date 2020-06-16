package br.com.feirasverdes.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.util.ImagemUtils;

@Service
public class FeiraService {
	
	@Autowired
	private FeiraDao dao;
	
	@Autowired
	private AvaliacaoDao avaliacaodao;
	
	@Autowired
	private NoticiaDao noticiadao;
	
	
	public Feira atualizarFeira(FeiraDto feiraDto) throws IOException {
		Feira feira = dao.getOne(feiraDto.getId());
		feira.setHora_inicio(feiraDto.getHora_inicio());
		feira.setHora_fim(feiraDto.getHora_fim());
		feira.setEndereco(feiraDto.getEndereco());
		feira.setFrequencia(feiraDto.getFrequencia());
		feira.setNome(feiraDto.getNome());
		feira.setUsuario(new Usuario(feiraDto.getIdUsuario()));
		feira.setTelefone(feiraDto.getTelefone());
		
		if (feiraDto.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = feiraDto.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(ImagemUtils.compressBytes(foto.getBytes()));
			feira.setImagem(imagem);
		}
		
		return dao.save(feira);
	}
	
	
}
