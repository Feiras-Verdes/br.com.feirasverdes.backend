package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "noticia")
public class Noticia  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="titulo", nullable = false, length = 200)
	private String titulo;
	
	@Column(name="descricao", nullable = false, length = 200)
	private String descricao;
	
	@Column(name="foto", nullable = false, length = 200)
	private String foto;
	
	private Estande estande;
	private Feira feira;
	
	public Noticia() {
		super();
	}

	public Noticia(Long id, String titulo, String descricao, String foto, Estande estande, Feira feira) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.foto = foto;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
