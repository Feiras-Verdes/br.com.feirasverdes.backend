package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

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

@Entity
@Table(name = "noticia")
public class Noticia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull(message = "Titulo de noticia não pode ser vazio")
	@Column(name = "titulo", nullable = false, length = 200)
	private String titulo;
	
	@NotNull(message = "Descrição de noticia não pode ser vazio")
	@Column(name = "descricao", nullable = false, length = 200)
	private String descricao;

	@OneToOne
	private Imagem imagem;

	@ManyToOne
	@JoinColumn(name = "id_estande")
	private Estande estande;

	@ManyToOne
	@JoinColumn(name = "id_feira")
	private Feira feira;

	public Noticia() {
		super();
	}

	public Noticia(Long id, String titulo, String descricao, Imagem imagem, Estande estande, Feira feira) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.imagem = imagem;
		this.estande = estande;
		this.feira = feira;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

}
