package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estande")
public class Estande implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="hora_inicio", nullable = false, length = 200)
	private String hora_inicio;
	
	@Column(name="frequencia", nullable = false, length = 200)
	private String frequencia;
	
	@Column(name="hora_fim", nullable = false, length = 200)
	private String hora_fim;
	
	private Feira feira;
	
	private Usuario usuario;

	

	public Estande(Long id, String hora_inicio, String frequencia, String hora_fim, Feira feira, Usuario usuario) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.frequencia = frequencia;
		this.hora_fim = hora_fim;
		this.feira = feira;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public String getHora_fim() {
		return hora_fim;
	}

	public void setHora_fim(String hora_fim) {
		this.hora_fim = hora_fim;
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

	public Usuario getFeirante() {
		return usuario;
	}

	public void setFeirante(Usuario feirante) {
		this.usuario = feirante;
	}
	
	
	
}
