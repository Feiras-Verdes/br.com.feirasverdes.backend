package br.com.feirasverdes.backend.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class AtualizarEstandeDto implements Serializable {
	
	private Long id;

	private String hora_inicio;

	private String frequencia;

	private String hora_fim;
	
	private String telefone;
	
	private String nome;

	private MultipartFile imagem;

	public AtualizarEstandeDto() {
		super();
		
	}

	public AtualizarEstandeDto(Long id, String hora_inicio, String frequencia, String hora_fim, String telefone,
			String nome, MultipartFile imagem) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.frequencia = frequencia;
		this.hora_fim = hora_fim;
		this.telefone = telefone;
		this.nome = nome;
		this.imagem = imagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public String getHora_fim() {
		return hora_fim;
	}

	public void setHora_fim(String hora_fim) {
		this.hora_fim = hora_fim;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

}
