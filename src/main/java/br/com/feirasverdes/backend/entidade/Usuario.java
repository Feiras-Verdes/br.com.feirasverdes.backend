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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_tipo_usuario")
	private TipoUsuario tipo;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private String nome;

	@Column()
	private String cpf;

	@Column()
	private String cnpj;

	@Column(nullable = false)
	private String telefone;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private boolean flag_ativo;

	@OneToOne
	private Imagem imagem;

	public Usuario() {
		super();
	}

	public Usuario(Long id, TipoUsuario tipo, String senha, String nome, String cpf, String cnpj, String telefone,
			String email, boolean flag_ativo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.flag_ativo = flag_ativo;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
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

	public boolean isFlag_ativo() {
		return flag_ativo;
	}

	public void setFlag_ativo(boolean flag_ativo) {
		this.flag_ativo = flag_ativo;
	}

}
