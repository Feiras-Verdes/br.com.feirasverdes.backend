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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estande")
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

	@ManyToOne
	@JoinColumn(name = "id_feira")
	private Feira feira;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@OneToOne
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@OneToOne
	private Imagem imagem;

	@OneToMany(mappedBy = "estande", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Noticia> noticias;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "vende", joinColumns = {
			@JoinColumn(name = "id_produto", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_estande", referencedColumnName = "id") })
	private List<Produto> produtos;

	public Estande(Long id, String hora_inicio, String frequencia, String hora_fim, Feira feira, Usuario usuario,
			Endereco endereco, Imagem imagem) {
		super();
		this.id = id;
		this.hora_inicio = hora_inicio;
		this.frequencia = frequencia;
		this.hora_fim = hora_fim;
		this.feira = feira;
		this.usuario = usuario;
		this.endereco = endereco;
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

	public Feira getFeira() {
		return feira;
	}

	public void setFeira(Feira feira) {
		this.feira = feira;
	}

	public Usuario getFeirante() {
		return usuario;
	}

	public void setFeirante(Usuario feirante) {
		this.usuario = feirante;
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

	public List<Noticia> getNoticias() {
		return this.noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

}
