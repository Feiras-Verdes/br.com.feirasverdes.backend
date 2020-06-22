package br.com.feirasverdes.backend.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UsuarioDto implements Serializable {

	private String nome;

	private String cpf;

	private String cnpj;

	private String telefone;

	private String email;

	private Date dataNascimento;

	private boolean ativo;

	private MultipartFile imagem;

	public UsuarioDto() {
		super();
	}

	public UsuarioDto(String nome, String cpf, String cnpj, String telefone, String email, Date dataNascimento,
			boolean ativo, MultipartFile imagem) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.ativo = ativo;
		this.imagem = imagem;
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
		return this.imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

}
