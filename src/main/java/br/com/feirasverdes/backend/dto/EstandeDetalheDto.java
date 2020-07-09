package br.com.feirasverdes.backend.dto;

import java.io.Serializable;

import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Usuario;

public class EstandeDetalheDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String horaInicio;

	private String frequencia;

	private String horaFim;

	private String telefone;

	private String nome;

	private Feira feira;

	private Endereco endereco;

	private Usuario usuario;

	private Imagem imagem;

	private Double avaliacao;
	
	public EstandeDetalheDto(Long id, String horaInicio, String frequencia, String horaFim, String telefone,
			String nome, Feira feira, Endereco endereco, Usuario usuario, Imagem imagem, Number avaliacao) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.frequencia = frequencia;
		this.horaFim = horaFim;
		this.telefone = telefone;
		this.nome = nome;
		this.feira = feira;
		this.endereco = endereco;
		this.usuario = usuario;
		this.imagem = imagem;
		if (avaliacao != null) {
			this.avaliacao = avaliacao.doubleValue();
		}
	}

	public EstandeDetalheDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
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

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}

}
