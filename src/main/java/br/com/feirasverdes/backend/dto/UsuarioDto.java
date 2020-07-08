package br.com.feirasverdes.backend.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.entidade.Imagem;

public class UsuarioDto implements Serializable {

	@NotBlank(message = "Nome não pode ser vazio")
	private String nome;

	@CPF(message = "CPF Incorreto")
	private String cpf;

	@CNPJ(message = "CNPJ Incorreto")
	private String cnpj;

	private String telefone;

	@NotBlank(message = "E-mail não pode ser vazio")
	private String email;

	private Date dataNascimento;

	private boolean ativo;

	private MultipartFile imagem;

	public UsuarioDto() {
		super();
	}
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		if (cpf == null || cpf.length() == 0) {
			this.cpf = null;
		} else {
			this.cpf = cpf;
		}
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

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return this.dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public MultipartFile getImagem() {
		return imagem;
	}
	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}
}
