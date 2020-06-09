package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Usuario;

@Repository
public interface EstandeDao extends JpaRepository<Estande,Long>{
	@Transactional
    @Modifying
    @Query(value = "select * from estande u where nome like '%?1%'", nativeQuery = true)
	List<Estande>pesquisarPorNome(String nome);

	Estande getOne(long id);
	
	void delete(Estande entity);
	
	void deleteById(Long id);
	
	List<Estande> findAll();
	
}