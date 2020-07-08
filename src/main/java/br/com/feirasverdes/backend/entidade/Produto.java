package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "produto")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Produto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Nome do produto não pode ser vazio")
	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "descricao", length = 200)
	private String descricao;

	@Column(name = "preco", nullable = false, length = 200)
	private Float preco;

	@Column(name = "unidade", nullable = false, length = 200)
	private String unidade;

	@OneToOne(cascade = CascadeType.ALL)
	private Imagem imagem;

	@ManyToOne
	@JoinColumn(name = "id_estande")
	private Estande estande;

	public Produto() {
		super();
	}

	public Produto(Long id, String nome, String descricao, Float preco, String unidade, Imagem imagem,
			Estande estande) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.unidade = unidade;
		this.imagem = imagem;
		this.estande = estande;
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

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Estande getEstande() {
		return estande;
	}

	public void setEstande(Estande estande) {
		this.estande = estande;
	}

}
