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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@Column(name = "hora_inicio", nullable = false, length = 200)
	private String hora_inicio;

	@Column(name = "frequencia", nullable = false, length = 200)
	private String frequencia;

	@Column(name = "hora_fim", nullable = false, length = 200)
	private String hora_fim;
	
	@Column(name = "telefone", nullable = false, length = 200)
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

	@OneToOne
	private Imagem imagem;

	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Noticia> noticias;
	
	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacoes;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "vende", joinColumns = {
			@JoinColumn(name = "id_produto", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_estande", referencedColumnName = "id") })
	@JsonIgnore
	private List<Produto> produtos;
	
	
	public Estande(Long id, String hora_inicio, String frequencia, String hora_fim, String telefone,
			@NotNull(message = "Nome do estande não pode ser vazio") String nome, Feira feira, Endereco endereco,
			Usuario usuario, Imagem imagem, List<Noticia> noticias, List<Avaliacao> avaliacoes,
			List<Produto> produtos) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.frequencia = frequencia;
		this.hora_fim = hora_fim;
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

	public Estande() {
		super();
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
		return "Estande [id=" + id + ", hora_inicio=" + hora_inicio + ", frequencia=" + frequencia + ", hora_fim="
				+ hora_fim + ", telefone=" + telefone + ", nome=" + nome + ", feira=" + feira + ", usuario=" + usuario
				+ ", imagem=" + imagem + ", noticias=" + noticias + ", produtos=" + produtos
				+ "]";
	}

}
