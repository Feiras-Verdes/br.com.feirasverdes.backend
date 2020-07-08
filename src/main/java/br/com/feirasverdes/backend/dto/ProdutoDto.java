package br.com.feirasverdes.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.entidade.Imagem;

public class ProdutoDto {

	private Long id;

	private String nome;

	private String descricao;

	private Float preco;

	private String unidade;

	private MultipartFile imagem;

	public ProdutoDto() {
		super();
	}

	public ProdutoDto(Long id, String nome, String descricao, Float preco, String unidade, MultipartFile imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.unidade = unidade;
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

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

}
