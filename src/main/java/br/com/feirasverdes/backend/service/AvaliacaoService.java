package br.com.feirasverdes.backend.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.entidade.Avaliacao;

@Service
@Transactional
public class AvaliacaoService {

	@Autowired
	AvaliacaoDao dao;

	public Avaliacao avaliar(@Valid Avaliacao avaliacao) {
		return dao.save(avaliacao);
	}

	public void atualizarAvaliacao(@Valid Long id, Avaliacao avaliacao) {
		// TODO verificar se usuário chamando método foi quem criou
		avaliacao.setId(id);
		dao.save(avaliacao);
	}

	public void excluirAvaliacao(Long id) {
		// TODO verificar se usuário chamando método foi quem criou
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

}
