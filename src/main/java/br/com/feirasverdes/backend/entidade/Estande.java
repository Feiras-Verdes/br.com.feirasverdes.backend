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
@Table(name = "estande")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Estande implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "hora_inicio", length = 200)
	private String horaInicio;

	@Column(name = "frequencia", length = 200)
	private String frequencia;

	@Column(name = "hora_fim", length = 200)
	private String horaFim;

	@Column(name = "telefone", length = 200)
	private String telefone;

	@NotNull(message = "Nome do estande não pode ser vazio")
	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_feira")
	private Feira feira;

	@ManyToOne
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@OneToOne(cascade = CascadeType.ALL)
	private Imagem imagem;

	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Noticia> noticias;

	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Avaliacao> avaliacoes;

	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Produto> produtos;

	public Estande(Long id, String horaInicio, String frequencia, String horaFim, String telefone,
			@NotNull(message = "Nome do estande não pode ser vazio") String nome, Feira feira, Endereco endereco,
			Usuario usuario, Imagem imagem, List<Noticia> noticias, List<Avaliacao> avaliacoes,
			List<Produto> produtos) {
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
		this.noticias = noticias;
		this.avaliacoes = avaliacoes;
		this.produtos = produtos;
	}

	public Estande(Long id) {
		super();
		this.id = id;
	}

	public Estande() {
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

	public List<Noticia> getNoticias() {
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	@Override
	public String toString() {
		return "Estande [id=" + id + ", hora_inicio=" + horaInicio + ", frequencia=" + frequencia + ", hora_fim="
				+ horaFim + ", telefone=" + telefone + ", nome=" + nome + ", feira=" + feira + ", usuario=" + usuario
				+ ", imagem=" + imagem + ", noticias=" + noticias + ", produtos=" + produtos + "]";
	}

}
