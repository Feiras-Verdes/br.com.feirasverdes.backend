package br.com.feirasverdes.backend.dto;


<<<<<<< HEAD
import java.time.LocalDateTime;

=======
>>>>>>> branch 'imagemBusca' of https://github.com/Feiras-Verdes/br.com.feirasverdes.backend.git
import org.springframework.web.multipart.MultipartFile;

public class NoticiaDto {

	private Long id;

	private String titulo;

	private String descricao;

	private MultipartFile imagem;
<<<<<<< HEAD
	
	private LocalDateTime dataPublicacao;
=======
>>>>>>> branch 'imagemBusca' of https://github.com/Feiras-Verdes/br.com.feirasverdes.backend.git

<<<<<<< HEAD
	public NoticiaDto(Long id, String titulo, String descricao, MultipartFile imagem, LocalDateTime dataPublicacao) {
=======
	public NoticiaDto(Long id, String titulo, String descricao, MultipartFile imagem) {
>>>>>>> branch 'imagemBusca' of https://github.com/Feiras-Verdes/br.com.feirasverdes.backend.git
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
