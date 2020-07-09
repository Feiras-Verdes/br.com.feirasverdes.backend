package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "feira")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Feira implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "hora_inicio", nullable = true, length = 200)
	private String horaInicio;

	@NotNull(message = "Nome de feira não pode ser vazio")
	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "frequencia", nullable = true, length = 200)
	private String frequencia;

	@Column(name = "hora_fim", nullable = true, length = 200)
	private String horaFim;

	@Column(name = "telefone", nullable = true, length = 200)
	private String telefone;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "feira", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Estande> estandes;

	@OneToOne(cascade = CascadeType.ALL)
	private Imagem imagem;

	@OneToMany(mappedBy = "feira", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Noticia> noticias;

	@OneToMany(mappedBy = "feira", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Avaliacao> avaliacoes;

	public Feira() {
		super();
	}
	
	public Feira(Long id) {
		super();
		this.id = id;
	}

	public Feira(Long id, String horaInicio, String nome, String frequencia, String horaFim, String telefone,
			Endereco endereco, Usuario usuario, Imagem imagem) {
		super();
		this.id = id;
		this.horaInicio = horaInicio;
		this.nome = nome;
		this.frequencia = frequencia;
		this.horaFim = horaFim;
		this.telefone = telefone;
		this.endereco = endereco;
		this.usuario = usuario;
		this.imagem = imagem;
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

	public void setHoraInicio(String hora_inicio) {
		this.horaInicio = hora_inicio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public void setHoraFim(String hora_fim) {
		this.horaFim = hora_fim;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Estande> getEstandes() {
		return this.estandes;
	}

	public void setEstandes(List<Estande> estandes) {
		this.estandes = estandes;
	}

	public List<Noticia> getNoticias() {
		return this.noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

}
