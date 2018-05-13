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
@Table(uniqueConstraints = { @UniqueConstraint(name = "proveedor_ruc_uk", columnNames = { "ruc" }) })
public class Proveedor extends GenericEntity {
	private static final String SECUENCIA = "proveedor_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@NotNull
	@Size(max = 50)
	private String nombre;
	@NotNull
	@Size(max = 50)
	private String ruc;
	@Size(max = 50)
	@NotNull
	private String nombre_contacto;
	@Size(max = 15)
	private String nroTelefono;
	@Size(max = 30)
	private String correoElectronico;
	@Size(max = 100)
	private String direccion;
	private String estado;
	
	public Proveedor() {
		super();
	}
	public Proveedor(Long id, @Size(max = 50) String nombre, @Size(max = 50) String ruc,
			@Size(max = 50) String nombre_contacto,@Size(max = 15) String nroTelefono, @Size(max = 30) String correoElectronico,
			@Size(max = 100) String direccion, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ruc = ruc;
		this.nroTelefono = nroTelefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.estado = estado;
	}


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNombre_contacto() {
		return nombre_contacto;
	}
	public void setNombre_contacto(String nombre_contacto) {
		this.nombre_contacto = nombre_contacto;
	}
	public String getNroTelefono() {
		return nroTelefono;
	}
	public void setNroTelefono(String nroTelefono) {
		this.nroTelefono = nroTelefono;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	public static String getSecuencia() {
		return SECUENCIA;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Proveedor [id=" + id + ", nombre=" + nombre + ", ruc=" + ruc + ", nombre_contacto=" + nombre_contacto
				+ ", nroTelefono=" + nroTelefono + ", correoElectronico=" + correoElectronico + ", direccion="
				+ direccion + ", estado=" + estado + "]";
	}
	
}
