package br.com.feirasverdes.backend.dto;

import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Imagem;

public class EstabelecimentoDto {
	
	private Imagem imagem;
	
	private String nome;
	
	private double mediaAvaliacao;
	
	private Endereco endereco;
	
	private String contato;

	public EstabelecimentoDto() {
		super();
	}

	public EstabelecimentoDto(Imagem imagem, String nome, double mediaAvaliacao, Endereco endereco, String contato) {
		super();
		this.imagem = imagem;
		this.nome = nome;
		this.mediaAvaliacao = mediaAvaliacao;
		this.endereco = endereco;
		this.contato = contato;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getMediaAvaliacao() {
		return mediaAvaliacao;
	}

	public void setMediaAvaliacao(double mediaAvaliacao) {
		this.mediaAvaliacao = mediaAvaliacao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}
		
}
