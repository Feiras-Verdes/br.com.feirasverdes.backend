package br.com.feirasverdes.backend.dto;

import br.com.feirasverdes.backend.entidade.Estande;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;

public class NoticiaDetalhesDto {

	private Long id;

	private String titulo;

	private String descricao;

	private String dataPublicacao;

	private Imagem imagem;

	private Estande estande;

	private Feira feira;

	public NoticiaDetalhesDto() {
		super();
	}

	public NoticiaDetalhesDto(Long id, String titulo, String descricao, String dataPublicacao, Imagem imagem,
			Estande estande, Feira feira) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataPublicacao = dataPublicacao;
		this.imagem = imagem;
		this.estande = estande;
		this.feira = feira;
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

	public String getDataPublicacao() {
		return this.dataPublicacao;
	}

	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Estande getEstande() {
		return estande;
	}

	public void setEstande(Estande estande) {
		this.estande = estande;
	}

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

}
