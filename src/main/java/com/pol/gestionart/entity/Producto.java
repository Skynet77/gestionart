package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table

public class Producto extends GenericEntity {
	
	private static final String SECUENCIA = "producto_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idProducto;
	@NotNull
	
	private String tipoProducto;
	@Size(max = 100)
	private String descripcion;
	@Size(max = 50)
	private String marca;
	@Size(max = 5)
	private String capacidad;
	@Size(max = 50)
	private Integer precioCompra;
	@Size(max = 50)
	private Integer precioVenta;
	@Size(max = 50)
	private Integer iva;
	@Size(max = 50)
	private Integer cantidad;
	
	public Long getIdProducto() {
		return idProducto;
	}
	
	public Producto() {
		super();
	}

	
	public Producto(Long idProducto, @NotNull char tipoProducto, @Size(max = 100) String descripcion,
			@Size(max = 50) String marca, @Size(max = 5) String capacidad, @Size(max = 50) Integer precioCompra,
			@Size(max = 50) Integer precioVenta, @Size(max = 50) Integer iva, @Size(max = 50) Integer cantidad) {
		super();
		this.idProducto = idProducto;
		this.tipoProducto = "";
		this.descripcion = descripcion;
		this.marca = marca;
		this.capacidad = capacidad;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.iva = iva;
		this.cantidad = cantidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	public Integer getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(Integer precioCompra) {
		this.precioCompra = precioCompra;
	}
	public Integer getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(Integer precioVenta) {
		this.precioVenta = precioVenta;
	}
	public Integer getIva() {
		return iva;
	}
	public void setIva(Integer iva) {
		this.iva = iva;
	}
	public static String getSecuencia() {
		return SECUENCIA;
	}

	@Override
	public Long getId() {
		return idProducto;
	}

	@Override
	public void setId(Long id) {
		this.idProducto = idProducto;
		
	}
	
	
	
	

}
