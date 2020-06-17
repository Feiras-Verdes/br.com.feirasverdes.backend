package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "feira")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Feira implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "hora_inicio", nullable = false, length = 200)
	private String hora_inicio;

	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "frequencia", nullable = false, length = 200)
	private String frequencia;

	@Column(name = "hora_fim", nullable = false, length = 200)
	private String hora_fim;

	@Column(name = "telefone", nullable = false, length = 200)
	private String telefone;

	@OneToOne
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_uusuario")
	private Usuario usuario;

	@OneToMany(mappedBy = "feira", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Estande> estandes;

	@OneToOne
	private Imagem imagem;

	@OneToMany(mappedBy = "feira", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Noticia> noticias;
	
	@OneToMany(mappedBy = "feira", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Avaliacao> avaliacoes;

	public Feira() {
		super();
	}

	public Feira(Long id, String hora_inicio, String nome, String frequencia, String hora_fim, String telefone,
			Endereco endereco, Usuario usuario, Imagem imagem) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.nome = nome;
		this.frequencia = frequencia;
		this.hora_fim = hora_fim;
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

	public String getHora_inicio() {
		return hora_inicio;
	}

	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Estande> getEstande() {
		return estandes;
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

}
