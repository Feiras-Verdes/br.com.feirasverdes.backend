package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="rua", nullable = false, length = 120)
	private String rua;
	
	@Column(name="numero",nullable = false)
	private int numero;
	
	@Column(name="bairro", length = 100)
	private String bairro;
	
	@Column(name="cidade", nullable = false, length = 100)
	private String cidade;
	
	@Column(name="cep",nullable = false, length = 45)
	private String cep;
	
	@Column(name="complemento", length = 100)
	private String complemento;
	
	@Column(name="estado", nullable = false, length = 2)
	private String estado;
	
	public Endereco() {
		super();
	}

	public Endereco(Long id, String rua, int numero, String bairro, String cidade, String cep, String complemento,
			String estado) {
		super();
		this.id = id;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.complemento = complemento;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
