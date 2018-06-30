package com.pol.gestionart.security;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotEmpty;

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
	@NotEmpty(message = "usuario.cedula_ruc.notBlank")
	@Size(max = 20, message = "usuario.cedula_ruc.size")
	private String cedulaRuc;

	@NotNull(message = "usuario.nombreRazonSocial.notNull")
	@NotEmpty(message = "usuario.nombreRazonSocial.notBlank")
	@Size(max = 60, message = "usuario.nombreRazonSocial.size")
	private String nombreRazonSocial;

	@Size(max = 60, message = "usuario.apellido.size")
	private String apellido;

	@Size(max = 256, message = "usuario.password.size")
	private String password;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "usuario_rol_fk"))
	@NotNull(message = "usuario.rol.notNull")
	private Rol rol;
	
	@Size(max = 1)
	private String estado;
	
	public Usuario() {
		super();
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
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
		if(estado == null){
			this.estado = "I";
		}else{
			this.estado = estado;
		}
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", cedulaRuc=" + cedulaRuc + ", nombreRazonSocial=" + nombreRazonSocial
				+ ", apellido=" + apellido + ", password=" + password + ", rol=" + rol + "]";
	}
}

	