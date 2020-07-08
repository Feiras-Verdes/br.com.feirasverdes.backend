package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Imagem;

@JsonInclude(Include.NON_NULL)
public class ListFeiraDTO implements Serializable {

	private Long id;
	private String nome;
	private Double avaliacao;
	private Endereco endereco;
	private Imagem imagem;

	public ListFeiraDTO() {
		super();
	}

	public ListFeiraDTO(Long id, String nome, Number avaliacao, Endereco endereco, Imagem imagem) {
		super();
		this.id = id;
		this.nome = nome;
		if (avaliacao != null) {
			this.avaliacao = avaliacao.doubleValue();
		}
		this.endereco = endereco;
		this.imagem = imagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

}
