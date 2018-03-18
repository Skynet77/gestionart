package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table 
public class Cliente {
	
	private static final String SECUENCIA = "cliente_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idCliente;
	
	@Size(max = 50)
	private String nombre;
	@Size(max = 50)
	private String apellido;
	@Size(max = 50)
	private String ruc;
	@Size(max = 15)
	private String nroTelefono;
	@Size(max = 30)
	private String correoElectronico;
	@Size(max = 100)
	private String direccion;
	private char estado;
	
	public Cliente() {
		super();
	}
	
	public Cliente(Long idCliente, String nombre, String apellido, String ruc, String nroTelefono, String correoElectronico,
		String direccion, char estado) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.ruc = ruc;
		this.nroTelefono = nroTelefono;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.estado = estado;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
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

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", apellido=" + apellido + ", ruc=" + ruc
				+ ", nroTelefono=" + nroTelefono + ", correoElectronico=" + correoElectronico + ", direccion="
				+ direccion + ", estado=" + estado + "]";
	}
	
	
	
	
	
}
