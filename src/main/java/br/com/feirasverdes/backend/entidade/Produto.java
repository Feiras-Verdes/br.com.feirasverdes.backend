package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "descricao", nullable = false, length = 200)
	private String descricao;

	@Column(name = "preco", nullable = false, length = 200)
	private Float preco;

	@ManyToMany(mappedBy = "produtos", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Estande> estandes;

	public Produto() {
		super();
	}

	public Produto(Long id, String nome, String descricao, Float preco, List<Estande> estandes) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.estandes = estandes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public List<Estande> getFeirante() {
		return estandes;
	}

	public void setFeirante(List<Estande> estandes) {
		this.estandes = estandes;
	}

}
