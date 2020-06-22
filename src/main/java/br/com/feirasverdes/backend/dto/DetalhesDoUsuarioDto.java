package br.com.feirasverdes.backend.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.TipoUsuario;

@JsonInclude(Include.NON_NULL)
public class DetalhesDoUsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;

	private String nome;

	private Imagem imagem;

	private String cpf;

	private String cnpj;

	private String telefone;

	private Date dataNascimento;

	private TipoUsuario tipoUsuario;

	private DetalhesDoUsuarioDto(final Builder builder) {
		id = builder.id;
		email = builder.email;
		nome = builder.nome;
		imagem = builder.imagem;
		cpf = builder.cpf;
		cnpj = builder.cnpj;
		telefone = builder.telefone;
		dataNascimento = builder.dataNascimento;
		tipoUsuario = builder.tipoUsuario;
	}
	
	public DetalhesDoUsuarioDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setUserId(final Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(final Imagem imagem) {
		this.imagem = imagem;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public TipoUsuario getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private Long id;

		private String email;

		private String nome;

		private Imagem imagem;

		private String cpf;

		private String cnpj;

		private String telefone;

		private Date dataNascimento;

		private TipoUsuario tipoUsuario;

		private Builder() {
		}

		public Builder withId(final Long id) {
			this.id = id;
			return this;
		}

		public Builder withEmail(final String email) {
			this.email = email;
			return this;
		}

		public Builder withNome(final String nome) {
			this.nome = nome;
			return this;
		}

		public Builder withImagem(final Imagem imagemUrl) {
			this.imagem = imagemUrl;
			return this;
		}

		public Builder withCpf(final String cpf) {
			this.cpf = cpf;
			return this;
		}

		public Builder withCnpj(final String cnpj) {
			this.cnpj = cnpj;
			return this;
		}

		public Builder withTelefone(final String telefone) {
			this.telefone = telefone;
			return this;
		}

		public Builder withDataNascimento(final Date dataNascimento) {
			this.dataNascimento = dataNascimento;
			return this;
		}

		public Builder withTipoUsuario(final TipoUsuario tipoUsuario) {
			this.tipoUsuario = tipoUsuario;
			return this;
		}

		public DetalhesDoUsuarioDto build() {
			return new DetalhesDoUsuarioDto(this);
		}
	}

}
