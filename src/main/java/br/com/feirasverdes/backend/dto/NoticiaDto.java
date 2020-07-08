package br.com.feirasverdes.backend.dto;


import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

public class NoticiaDto {

	private Long id;

	private String titulo;

	private String descricao;

	private MultipartFile imagem;
	
	private LocalDateTime dataPublicacao;

	public NoticiaDto(Long id, String titulo, String descricao, MultipartFile imagem, LocalDateTime dataPublicacao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.imagem = imagem;
		this.dataPublicacao = dataPublicacao;
	}

	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDateTime dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
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
