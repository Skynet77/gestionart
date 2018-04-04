package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.pol.gestionart.bean.GenericEntity;

@Entity
@Table

public class Proveedor extends GenericEntity {
	private static final String SECUENCIA = "proveedor_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idProveedor;
	
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
	private char estado;
	
	public Proveedor() {
		super();
	}
	public Proveedor(Long idProveedor, @Size(max = 50) String nombre, @Size(max = 50) String ruc,
			@Size(max = 15) String nroTelefono, @Size(max = 30) String correoElectronico,
			@Size(max = 100) String direccion, char estado) {
		super();
		this.idProveedor = idProveedor;
		this.nombre = nombre;
		this.ruc = ruc;
		this.nroTelefono = nroTelefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.estado = estado;
	}
	public Long getIdProveedor() {
		return idProveedor;
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
	public static String getSecuencia() {
		return SECUENCIA;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	

}
