package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nota", nullable = false, length = 200)
	private Double nota;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_estande")
	private Estande estande;

	@ManyToOne
	@JoinColumn(name = "id_feira")
	private Feira feira;

	public Avaliacao() {
		super();
	}

	public Avaliacao(Long id, Double nota, Usuario usuario, Estande estande, Feira feira) {
		super();
		this.id = id;
		this.nota = nota;
		this.usuario = usuario;
		this.estande = estande;
		this.feira = feira;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
