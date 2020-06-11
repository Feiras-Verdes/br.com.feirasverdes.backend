package br.com.feirasverdes.backend.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tipo_usuario")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class TipoUsuario implements Serializable {

	@Id
	@Column(nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String descricao;

	public TipoUsuario() {
		super();
	}

	public TipoUsuario(Integer id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
