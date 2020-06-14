package br.com.feirasverdes.backend.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.feirasverdes.backend.entidade.Usuario;

@Repository
public interface OrganizadorDao extends JpaRepository<Usuario, Long> {

}
