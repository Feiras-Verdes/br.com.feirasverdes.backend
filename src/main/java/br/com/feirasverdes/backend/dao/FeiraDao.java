package br.com.feirasverdes.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.feirasverdes.backend.dto.EstabelecimentoDto;
import br.com.feirasverdes.backend.dto.ListFeiraDTO;
import br.com.feirasverdes.backend.entidade.Feira;

public interface FeiraDao extends JpaRepository<Feira, Long> {

	@Query(value = "from Feira where upper(nome) like %?1%")
	List<Feira> pesquisarPorNome(String nome);

	// , avg( avaliacao.nota), count(avaliacao.id)
	@Query("select new br.com.feirasverdes.backend.dto.ListFeiraDTO(feira.id, feira.nome, COALESCE(avg(avaliacao.nota), 0), endereco, imagem) from Avaliacao avaliacao right join avaliacao.feira feira left join feira.endereco endereco left join feira.imagem imagem group by feira order by COALESCE(avg(avaliacao.nota), 0) desc")
	public List<ListFeiraDTO> buscarMelhoresFeiras();

	@Query(value = "from Feira feira where feira.usuario.id = ?1")
	public List<Feira> buscarFeirasDeUsuario(Long id);

	List<Feira> findByUsuarioId(Long idUsuario);

	@Query(value = "select new br.com.feirasverdes.backend.dto.EstabelecimentoDto"
			+ "(f.id, f.nome, f.telefone, i, e , (CEILING(AVG(a.nota) / 0.5) * 0.5) as avaliacao)" + " from Feira f "
			+ " left join f.endereco e" + " left join f.imagem i" + " left join f.avaliacoes a "
			+ "where upper(f.nome) like ?1 group by f.nome, f.id, f.telefone")
	Page<EstabelecimentoDto> buscaFeiraPorFiltro(String nome, Pageable pageable);

	@Query(value = "select COALESCE(avg(avaliacao.nota), 0) from Avaliacao avaliacao right join avaliacao.feira feira where feira.id = ?1")
	Number avaliacaoPorFeira(Long id);

}
