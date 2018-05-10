package com.pol.gestionart.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;




@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "cliente_ruc_uk", columnNames = { "ruc" }) })
public class Cliente extends GenericEntity{
	
	private static final String SECUENCIA = "cliente_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@Size(max = 50)
	private String nombre;
	@Size(max = 50)
	private String apellido;
	@Size(max = 10)
	private String fecha_nacimiento;
	@Size(max = 20)
	private String sexo;
	@Size(max = 50)
	private String ruc;
	@Size(max = 15)
	private String nroTelefono;
	@Size(max = 30)
	private String correoElectronico;
	@Size(max = 100)
	private String direccion;
	private String estado;
	
	public Cliente() {
		super();
	}
	
	public Cliente(Long id, @Size(max = 50) String nombre, @Size(max = 50) String apellido, String fecha_nacimiento,
			@Size(max = 20) String sexo, @Size(max = 50) String ruc, @Size(max = 15) String nroTelefono,
			@Size(max = 30) String correoElectronico, @Size(max = 100) String direccion, String estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
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
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fecha_nacimiento="
				+ fecha_nacimiento + ", sexo=" + sexo + ", ruc=" + ruc + ", nroTelefono=" + nroTelefono
				+ ", correoElectronico=" + correoElectronico + ", direccion=" + direccion + ", estado=" + estado + "]";
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public static String getSecuencia() {
		return SECUENCIA;
	}
	
	
	
	
	
}
