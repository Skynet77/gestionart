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

public class Proveedor {
	
	private static final String SECUENCIA = "proveedor_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idProveedor;
	
	@NotNull
	@Size(max = 50)
	private String nombre;
	@NotNull
	@Size(max = 30)
	private String ruc;
	@NotNull
	@Size(max = 15)
	private String nroTelefono;
	@NotNull
	@Size(max = 30)
	private String correoElectronico;
	@NotNull
	@Size(max = 100)
	private String direccion;
	@NotNull
	private char estado;

	
	public Proveedor() {
		super();
	}
	
	public Proveedor(Long idProveedor, @NotNull @Size(max = 50) String nombre,
			@NotNull @Size(max = 30) String ruc, @NotNull @Size(max = 15) String nroTelefono,
			@NotNull @Size(max = 30) String correoElectronico, @NotNull @Size(max = 100) 
			String direccion, @NotNull char estado) {
		super();
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.ruc = ruc;
		this.nroTelefono = nroTelefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.estado = estado;
	}
	
	
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
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
	
	public char getEstado() {
		return estado;
	}
	public void setEstado(char estado) {
		this.estado = estado;
	}

	
}