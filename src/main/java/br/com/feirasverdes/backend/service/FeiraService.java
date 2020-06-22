package br.com.feirasverdes.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EnderecoDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dto.EnderecoDto;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.util.ImagemUtils;

@Service
@Transactional
public class FeiraService {
	
	@Autowired
	private FeiraDao dao;
	
	@Autowired
	private EnderecoDao enderecoDao;
	
	@Autowired
	private AvaliacaoDao avaliacaodao;
	
	@Autowired
	private NoticiaDao noticiadao;
	
	public Feira cadastrarFeira(FeiraDto feiraDto) throws IOException {
		Feira feira = new Feira();
		paraFeira(feiraDto, feira);
		Endereco endereco = feira.getEndereco() != null ? feira.getEndereco() : new Endereco();
		paraEndereco(feiraDto.getEndereco(), endereco);
		endereco = enderecoDao.save(endereco);
		feira.setEndereco(endereco);
		return dao.save(feira);
	}
	
	public Feira atualizarFeira(FeiraDto feiraDto) throws IOException {
		Feira feira = dao.getOne(feiraDto.getId());
		paraFeira(feiraDto, feira);
		Endereco endereco = new Endereco();
		paraEndereco(feiraDto.getEndereco(), endereco);
		endereco = enderecoDao.save(endereco);
		feira.setEndereco(endereco);
		return dao.save(feira);
	}


	private void paraFeira(FeiraDto feiraDto, Feira feira) throws IOException {
		feira.setHora_inicio(feiraDto.getHora_inicio());
		feira.setHora_fim(feiraDto.getHora_fim());
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
	}
	
	private void paraEndereco(EnderecoDto enderecoDto, Endereco endereco) {
		endereco.setBairro(enderecoDto.getBairro());
		endereco.setCep(enderecoDto.getCep());
		endereco.setCidade(enderecoDto.getCidade());
		endereco.setComplemento(enderecoDto.getComplemento());
		endereco.setEstado(enderecoDto.getEstado());
		endereco.setNumero(enderecoDto.getNumero());
		endereco.setRua(enderecoDto.getRua());
	}
	
	
}
