package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.entidade.Feira;

public interface FeiraDao extends JpaRepository<Feira, Long> {

	@Query(value = "from Feira where upper(nome) like ?1")
	List<Feira> pesquisarPorNome(String nome);
	
	//, avg( avaliacao.nota), count(avaliacao.id)
	@Query("select feira from Avaliacao avaliacao right join avaliacao.feira feira group by feira order by COALESCE(avg(avaliacao.nota), 0) desc")
	public List<Feira> buscarMelhoresFeiras();
	
	@Query(value = "select new br.com.feirasverdes.backend.dto.EstabelecimentoDto"
			 + "(f.id, f.nome, f.telefone, i, e , (CEILING(AVG(a.nota) / 0.5) * 0.5)) "
			 + " from Feira f "
			 + " left join f.endereco e" 
			 + " left join f.imagem i"
		     + " left join f.avaliacoes a "  +
		       "where upper(f.nome) like ?1 group by f.nome, f.id, f.telefone, f.endereco")
   Page<EstabelecimentoDto> buscaFeiraPorFiltro(String nome, Pageable pageable);
}
