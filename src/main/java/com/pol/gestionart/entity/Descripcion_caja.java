package com.pol.gestionart.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
public class Descripcion_caja extends GenericEntity {
private static final String SECUENCIA = "Descripcion_caja_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@Size(max=6)
	private String codigo;
	
	@Size(max=30)
	private String descripcion;
	
	public Descripcion_caja() {
		super();
	}

	public Descripcion_caja(Long id, @Size(max = 6) String codigo, @Size(max = 30) String descripcion) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static String getSecuencia() {
		return SECUENCIA;
	}

	@Override
	public String toString() {
		return "Descripcion_caja [id=" + id + ", codigo=" + codigo + ", descripcion=" + descripcion + "]";
	}
	
	
	
}
