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

public class Proveedor extends GenericEntity {
	private static final String SECUENCIA = "proveedor_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@Size(max = 50)
	private String nombre;
	@Size(max = 50)
	private String ruc;
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
	public Proveedor(Long idProveedor, String nombre, String ruc,
			String nroTelefono, String correoElectronico,
			String direccion, String estado) {
		super();
		this.id = idProveedor;
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
		return "Proveedor [idProveedor=" + id + ", nombre=" + nombre + ", ruc=" + ruc + ", nroTelefono=" + nroTelefono
				+ ", correoElectronico=" + correoElectronico + ", direccion=" + direccion + ", estado=" + estado + "]";
	}
	
}
