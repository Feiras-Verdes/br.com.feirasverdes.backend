package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EnderecoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.util.ImagemUtils;

@Service
public class EstandeService {

	@Autowired
	private EstandeDao dao;
	
	@Autowired
	private EnderecoDao enderecoDao;
	
	public void atualizarEstande(final Long id, final EstandeDto estandeAtualizado) throws IOException{
		Estande estande = dao.getOne(id);

		if (estandeAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = estandeAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(ImagemUtils.compressBytes(foto.getBytes()));

			estande.setImagem(imagem);
		}
		estande.setHora_inicio(estandeAtualizado.getHoraInicio());
		estande.setFrequencia(estandeAtualizado.getFrequencia());
		estande.setHora_fim(estandeAtualizado.getHoraFim());
		estande.setNome(estandeAtualizado.getNome());
		estande.setTelefone(estandeAtualizado.getTelefone());
		
		Endereco endereco = estande.getEndereco() != null ? estande.getEndereco() : new Endereco();
		endereco.setBairro(estandeAtualizado.getBairro());
		endereco.setCep(estandeAtualizado.getCep());
		endereco.setCidade(estandeAtualizado.getCidade());
		endereco.setComplemento(estandeAtualizado.getComplemento());
		endereco.setEstado(estandeAtualizado.getEstado());
		endereco.setNumero(estandeAtualizado.getNumero());
		endereco.setLogradouro(estandeAtualizado.getLogradouro());
		enderecoDao.save(endereco);
		estande.setEndereco(endereco);
		dao.save(estande);
	}

	public List<Estande> buscarEstandesPorUsuario(Long usuarioId) {
		return dao.findByUsuarioId(usuarioId);
	}

}
