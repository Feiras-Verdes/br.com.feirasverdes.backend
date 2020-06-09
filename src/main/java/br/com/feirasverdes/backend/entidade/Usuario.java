package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="usuario", nullable = false, length = 200)
	private String usuario;
	
	//Os seguintes tipos sao:1- usuario, 2-Organizador, 3-feirante
	@Column(name="tipo", nullable = false, length = 10)
	private Integer tipo;
	
	@Column(name="senha", nullable = false, length = 200)
	private String senha;
	
	@Column(name="nome", nullable = false, length = 200)
	private String nome;
	
	@Column(name="cpf", nullable = false, length = 200)
	private String cpf;
	
	@Column(name="telefone", nullable = false, length = 200)
	private String telefone;
	
	@Column(name="email", nullable = false, length = 200)
	private String email;

	public Usuario() {
		super();
	}

	public Usuario(Long id, String usuario, String senha, String nome, String cpf, String telefone, String email,Integer tipo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.tipo= tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	
	
	
	
	
}
