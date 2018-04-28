package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;



@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "usuario_cedula_ruc_uk", columnNames = { "cedulaRuc" }) })
public class Usuario extends GenericEntity {
	private static final String SECUENCIA = "usuario_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;

	@NotNull(message = "usuario.cedula_ruc.notNull")
	@NotBlank(message = "usuario.cedula_ruc.notBlank")
	@Size(max = 20)
	private String cedulaRuc;
	@NotNull
	@Size(max = 60)
	private String nombreRazonSocial;
	@Size(max = 60)
	private String apellido;
	@NotNull
	@Size(max = 256)
	private String password;

	private String estado;

	
	public Usuario() {
		super();
	}

	public Usuario(Long id,
			@NotNull(message = "usuario.cedula_ruc.notNull") @Size(max = 20) String cedulaRuc,
			@NotNull @Size(max = 60) String nombreRazonSocial,
			@Size(max = 60) String apellido,
			@NotNull @Size(max = 256) String password, String estado) {
		super();
		this.id = id;
		this.cedulaRuc = cedulaRuc;
		this.nombreRazonSocial = nombreRazonSocial;
		this.apellido = apellido;
		this.password = password;
		this.estado = estado;
	}

	public String getCedulaRuc() {
		return cedulaRuc;
	}

	public void setCedulaRuc(String cedulaRuc) {
		this.cedulaRuc = cedulaRuc;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Long getId() {

		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + id + ", cedulaRuc=" + cedulaRuc + ", nombreRazonSocial=" + nombreRazonSocial
				+ ", apellido=" + apellido + ", password=" + password + "]";
	}
	
}
