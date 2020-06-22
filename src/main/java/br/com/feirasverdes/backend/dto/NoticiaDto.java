package br.com.feirasverdes.backend.dto;

import org.springframework.web.multipart.MultipartFile;

public class NoticiaDto {

	private Long id;

	private String titulo;

	private String descricao;

	private MultipartFile imagem;

	public NoticiaDto(Long id, String titulo, String descricao, MultipartFile imagem) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.imagem = imagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

}
