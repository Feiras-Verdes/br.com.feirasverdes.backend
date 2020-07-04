package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.dto.UsuarioDto;
import br.com.feirasverdes.backend.dto.DetalhesDoUsuarioDto;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.EmailInvalidoException;
import br.com.feirasverdes.backend.exception.TipoInvalidoException;
import br.com.feirasverdes.backend.util.ImagemUtils;

@Service
public class EstandeService {

	@Autowired
	private EstandeDao dao;

	public void atualizarEstande(final Long id, EstandeDto estandeAtualizado) throws IOException{
		
		Estande estande = dao.getOne(id);
		
		if (estandeAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = estandeAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(ImagemUtils.compressBytes(foto.getBytes()));
			
			estande.setImagem(imagem);
		}
		estande.setHora_inicio(estandeAtualizado.getHora_inicio());
		estande.setFrequencia(estandeAtualizado.getFrequencia());
		estande.setHora_fim(estandeAtualizado.getHora_fim());
		estande.setNome(estandeAtualizado.getNome());
		dao.save(estande);
	}

	public List<Estande> buscarEstandesPorUsuario(Long usuarioId) {
		return dao.findByUsuarioId(usuarioId);
	}

}
