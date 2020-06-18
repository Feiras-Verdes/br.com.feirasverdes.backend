package br.com.feirasverdes.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public class AtualizarProdutoDto {

	private Long id;

	private String nome;

	private String descricao;

	private Float preco;

	private MultipartFile imagem;
	
	public AtualizarProdutoDto() {
		super();
	}

	public AtualizarProdutoDto(Long id, String nome, String descricao, Float preco, MultipartFile imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

}
