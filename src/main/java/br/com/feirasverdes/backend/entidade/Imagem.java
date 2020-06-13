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

	@Column(name = "nome", nullable = true, length = 200)
	private String nome;

	@Column(name = "legenda", nullable = true, length = 200)
	private String legenda;

	@Column(name = "tipo", nullable = false, length = 200)
	private String tipo;

	@Column(name = "bytesImagem", nullable = false, length = 1000)
	private byte[] bytesImagem;

	public Imagem() {
		super();
	}

	public Imagem(Long id, String nome, String legenda, String tipo, byte[] bytesImagem) {
		this.id = id;
		this.nome = nome;
		this.legenda = legenda;
		this.tipo = tipo;
		this.bytesImagem = bytesImagem;
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

	public byte[] getBytesImagem() {
		return this.bytesImagem;
	}

	public void setBytesImagem(byte[] bytesImagem) {
		this.bytesImagem = bytesImagem;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
