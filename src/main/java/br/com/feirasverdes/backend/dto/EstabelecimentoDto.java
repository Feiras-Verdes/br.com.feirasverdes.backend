package br.com.feirasverdes.backend.dto;

import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Imagem;

public class EstabelecimentoDto {
	
	private Long id;
	private String nome;
	private String telefone;
	private Imagem imagem;

	public EstabelecimentoDto() {
		super();
	}

	public EstabelecimentoDto(Long id, String nome, String telefone, Imagem imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}


		
}
