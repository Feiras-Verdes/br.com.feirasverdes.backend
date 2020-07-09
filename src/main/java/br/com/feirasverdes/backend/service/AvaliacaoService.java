package br.com.feirasverdes.backend.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EstandeDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.entidade.Avaliacao;
import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.exception.AvaliacaoNaoPertenceAoUsuarioException;

@Service
@Transactional
public class AvaliacaoService {

	@Autowired
	AvaliacaoDao dao;

	@Autowired
	private EstandeDao estandeDao;

	@Autowired
	private FeiraDao feiraDao;

	public Avaliacao avaliar(@Valid Avaliacao avaliacao) {
		return dao.save(avaliacao);
	}

	public void atualizarAvaliacao(@Valid Long id, Avaliacao avaliacao) throws AvaliacaoNaoPertenceAoUsuarioException {
//		if(avaliacao.getEstande().getId() != null) {
//			Estande verificar = estandeDao.getOne(avaliacao.getEstande().getId());
//			if(verificar.getUsuario().getId().equals(id)) {
//				throw new AvaliacaoNaoPertenceAoUsuarioException("Não é possível avaliar seu próprio estande.");
//			}
//		}
//		
//		if(avaliacao.getFeira().getId() != null) {
//			Feira verificar = feiraDao.getOne(avaliacao.getFeira().getId());
//			if(verificar.getUsuario().getId().equals(id)) {
//				throw new AvaliacaoNaoPertenceAoUsuarioException("Não é possível avaliar sua própria feira.");
//			}
//		}
//		
		avaliacao.setId(id);
		dao.save(avaliacao);
	}

	public void excluirAvaliacao(Long id) throws AvaliacaoNaoPertenceAoUsuarioException {
		Avaliacao avaliacao = dao.getOne(id);

		/*
		 * if
		 * (!avaliacao.getUsuario().getEmail().equals(SecurityContextHolder.getContext()
		 * .getAuthentication().getName())) { throw new
		 * AvaliacaoNaoPertenceAoUsuarioException("Esta avaliação não foi cadastrada por você."
		 * ); }
		 */
		dao.deleteById(id);
	}

	public List<Avaliacao> listarTodos() {
		return dao.findAll();
	}

	public Avaliacao pesquisarPorId(Long id) {
		return dao.getOne(id);
	}

	public List<Avaliacao> listarAvaliacaoPorUsuario(Long idUsuario) {
		return dao.findByUsuarioId(idUsuario);
	}

	public Avaliacao avaliarEstande(Long idEstande, Avaliacao avaliacao) {
		avaliacao.setEstande(new Estande(idEstande));
		return dao.save(avaliacao);
	}

	public Avaliacao avaliarFeira(Long idFeira, Avaliacao avaliacao) {
		avaliacao.setFeira(new Feira(idFeira));
		return dao.save(avaliacao);
	}

}
