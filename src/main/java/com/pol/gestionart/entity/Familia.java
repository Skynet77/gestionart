package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.pol.gestionart.main.GenericEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "familia_codigo_uk", columnNames = { "codigo" })})
public class Familia extends GenericEntity {

	private static final String SECUENCIA = "familia_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA, allocationSize = 1)
	private Long id;

	@NotNull(message = "{familia.codigo.notNull}")
	@Size(max = 10, message = "{familia.codigo.size}")
	private String codigo;

	@NotNull(message = "{familia.nombre.notNull}")
	@Size(max = 100, message = "{familia.nombre.size}")
	private String nombre;


	public Familia() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
