package br.com.feirasverdes.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.feirasverdes.backend.entidade.Endereco;

public interface EnderecoDao extends JpaRepository<Endereco, Long> {

}
