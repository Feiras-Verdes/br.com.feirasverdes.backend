package br.com.feirasverdes.backend.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "imagem")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Imagem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "legenda", nullable = true, length = 200)
	private String legenda;

	@Column(name = "tipo", nullable = false, length = 200)
	private String tipo;

	@Column(name = "imagem", nullable = false, length = 1000)
	private byte[] imagem;

	public Imagem() {
		super();
	}

	public Imagem(Long id, String legenda, String tipo, byte[] imagem) {
		this.id = id;
		this.legenda = legenda;
		this.tipo = tipo;
		this.imagem = imagem;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLegenda() {
		return this.legenda;
	}

	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public byte[] getImagem() {
		return this.imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

}
