package br.com.feirasverdes.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.feirasverdes.backend.entidade.TipoUsuario;

@Repository
public interface TipoUsuarioDao extends JpaRepository<TipoUsuario, Long> {

}
