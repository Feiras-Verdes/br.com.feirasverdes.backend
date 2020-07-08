package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.EnderecoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dto.EstandeDto;
import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.exception.EstandeNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.exception.FeiraNaoPertenceAoUsuarioException;

@Service
public class EstandeService {

	@Autowired
	private EstandeDao dao;

	@Autowired
	private EnderecoDao enderecoDao;

	public void atualizarEstande(final Long id, final EstandeDto estandeAtualizado) throws IOException, EstandeNaoPertenceAoUsuarioException {
		Estande estande = dao.getOne(id);
		
		if (!estande.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new EstandeNaoPertenceAoUsuarioException("Esta feira não foi cadastrada por você.");
		}

		if (estandeAtualizado.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = estandeAtualizado.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());

			estande.setImagem(imagem);
		}
		estande.setHoraInicio(estandeAtualizado.getHoraInicio());
		estande.setFrequencia(estandeAtualizado.getFrequencia());
		estande.setHoraFim(estandeAtualizado.getHoraFim());
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

	public Estande cadastrarEstande(@Valid Estande estande) {
		return dao.save(estande);
	}

	public void excluirEstande(Long id) throws EstandeNaoPertenceAoUsuarioException {
		Estande estande = dao.getOne(id);
		
		if (!estande.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new EstandeNaoPertenceAoUsuarioException("Esta feira não foi cadastrada por você.");
		}
		dao.deleteById(id);
	}

	public List<Estande> listarTodos() {
		return dao.findAll();
	}

	public List<Estande> pesquisarPorNome(String nome) {
		return dao.pesquisarPorNome(nome);
	}

	public Estande pesquisarPorId(Long id) {
		return dao.getOne(id);
	}

	public List<Estande> buscarEstandesDeFeira(Long idFeira) {
		return dao.findByFeiraId(idFeira);
	}

}
