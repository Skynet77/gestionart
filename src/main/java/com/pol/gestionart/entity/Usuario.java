package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table

public class Usuario {
	
	private static final String SECUENCIA = "usuario_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idUsuario;
	
	@NotNull
	@Size(max = 30)
	private String rol;
	@Size(max = 30)
	private String alias;
	@Size(max = 20)
	private String contrasenha;
	
	private char estado;

	
	public Usuario() {
		super();
	}
	
	public Usuario(Long idUsuario, @NotNull @Size(max = 30) String rol, @Size(max = 30)
			String alias, @Size(max = 20) String contrasenha, char estado) {
		super();
		this.idUsuario = idUsuario;
		this.rol = rol;
		this.alias = alias;
		this.contrasenha = contrasenha;
		this.estado = estado;
	}
	
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getContrasenha() {
		return contrasenha;
	}
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	
	public char getEstado() {
		return estado;
	}
	public void setEstado(char estado) {
		this.estado = estado;
	}

	
}