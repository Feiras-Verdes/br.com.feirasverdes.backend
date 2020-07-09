package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;
import java.util.Date;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Tipo de usuário não pode ser vazio")
	@ManyToOne
	@JoinColumn(name = "id_tipo_usuario", nullable = false)
	private TipoUsuario tipoUsuario;

	@NotBlank(message = "Senha não pode ser vazia")
	@Column(nullable = false)
	private String senha;

	@NotBlank(message = "Nome não pode ser vazio")
	@Column(nullable = false)
	private String nome;

	@CPF(message = "CPF inválido")
	@Column(nullable = true)
	private String cpf;

	@CNPJ(message = "CNPJ inválido")
	@Column(nullable = true)
	private String cnpj;

	@Column(nullable = true)
	private String telefone;

	@NotBlank(message = "E-mail não pode ser vazio")
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = true)
	private Date dataNascimento;

	@Column(nullable = false)
	private boolean ativo;

	@OneToOne(cascade = CascadeType.ALL)
	private Imagem imagem;

	public Usuario() {
		super();
	}

	public Usuario(Long id, TipoUsuario tipoUsuario, String senha, String nome, String cpf, String cnpj,
			String telefone, String email, Date dataNascimento, boolean ativo, Imagem imagem) {
		super();
		this.id = id;
		this.tipoUsuario = tipoUsuario;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.ativo = ativo;
		this.imagem = imagem;
	}

	public Usuario(Long id) {
		super();
		this.id = id;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		if (cnpj == null || cnpj.length() == 0) {
			this.cnpj = null;
		} else {
			this.cnpj = cnpj;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
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
		if (cpf == null || cpf.length() == 0) {
			this.cpf = null;
		} else {
			this.cpf = cpf;
		}
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Imagem getImagem() {
		return this.imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

}
