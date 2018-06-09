package com.pol.gestionart.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
public class Inventario extends GenericEntity {
	private static final String SECUENCIA = "inventario_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	private Producto producto;
	
	private CompraCabecera compracabecera;
	
	private VentaCabecera ventacabecera;
	
	//private Caja caja;
	
	public Inventario() {
		super();
	}

	public Inventario(Long id, Producto producto, CompraCabecera compracabecera, VentaCabecera ventacabecera/*, Caja caja*/) {
		super();
		this.id = id;
		this.producto = producto;
		this.compracabecera = compracabecera;
		this.ventacabecera = ventacabecera;
		//this.caja = caja;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public CompraCabecera getCompracabecera() {
		return compracabecera;
	}

	public void setCompracabecera(CompraCabecera compracabecera) {
		this.compracabecera = compracabecera;
	}

	public VentaCabecera getVentacabecera() {
		return ventacabecera;
	}

	public void setVentacabecera(VentaCabecera ventacabecera) {
		this.ventacabecera = ventacabecera;
	}

/*	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}*/
	
	public static String getSecuencia() {
		return SECUENCIA;
	}

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", producto=" + producto + ", compracabecera=" + compracabecera
				+ ", ventacabecera=" + ventacabecera + "]";
	}
	
}

