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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.pol.gestionart.main.GenericEntity;


@Entity
@Table//(uniqueConstraints = { @UniqueConstraint(name = "ventaCabecera_id_uk", columnNames = { "venta_cab_id" }) })
public class VentaCabecera extends GenericEntity{
	
	private static final String SECUENCIA = "ventaCab_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idVenta;
	

	@NotNull
	@Size(max = 20)
	private String nroFactura;
	

	@NotNull
	@Size(max = 20)
	private String rucFactura;
	

	@NotNull
	@Size(max = 20)
	private String timbrado;


	@ManyToOne
	@NotNull(message = "{ventacabecera.producto.notNull}")
	@JoinColumn(foreignKey = @ForeignKey(name = "venta_producto_fk"))
	private Producto producto;
		
	@NotNull
	@Size(max = 60)
	private String nombreRazonSocial;

	@NotNull
	@Size(max = 20)
	private String cedulaRuc;

	@NotNull
	@Size(max = 20)
	private String fecha;

	@NotNull
	private Long montoTotal;


	@NotNull
	private Long subTotal;
	
	
	@NotNull
	private Float iva;
	

	public VentaCabecera() {
		super();
	}


	public VentaCabecera(Long idVenta, @NotNull @Size(max = 20) String nroFactura,
			@NotNull @Size(max = 20) String rucFactura, @NotNull @Size(max = 20) String timbrado,
			@NotNull(message = "{ventacabecera.producto.notNull}") Producto producto,
			@NotNull @Size(max = 60) String nombreRazonSocial, @NotNull @Size(max = 20) String cedulaRuc,
			@NotNull @Size(max = 20) String fecha, @NotNull Long montoTotal, @NotNull Long subTotal,
			@NotNull Float iva) {
		super();
		this.idVenta = idVenta;
		this.nroFactura = nroFactura;
		this.rucFactura = rucFactura;
		this.timbrado = timbrado;
		this.producto = producto;
		this.nombreRazonSocial = nombreRazonSocial;
		this.cedulaRuc = cedulaRuc;
		this.fecha = fecha;
		this.montoTotal = montoTotal;
		this.subTotal = subTotal;
		this.iva = iva;
	}


	public String getNroFactura() {
		return nroFactura;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public String getRucFactura() {
		return rucFactura;
	}

	public void setRucFactura(String rucFactura) {
		this.rucFactura = rucFactura;
	}

	public String getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}

	public Long getIdVenta() {
		return idVenta;
	}


	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public void setIva(Float iva) {
		this.iva = iva;
	}


	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public String getCedulaRuc() {
		return cedulaRuc;
	}

	public void setCedulaRuc(String cedulaRuc) {
		this.cedulaRuc = cedulaRuc;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Long montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Long getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Long subTotal) {
		this.subTotal = subTotal;
	}

	public static String getSecuencia() {
		return SECUENCIA;
	}
	
	@Override
	public String toString() {
		return "VentaCabecera [idVenta=" + idVenta + ", nroFactura=" + nroFactura + ", rucFactura=" + rucFactura
				+ ", timbrado=" + timbrado + ", producto=" + producto + ", nombreRazonSocial=" + nombreRazonSocial
				+ ", cedulaRuc=" + cedulaRuc + ", fecha=" + fecha + ", montoTotal=" + montoTotal + ", subTotal="
				+ subTotal + ", iva=" + iva + "]";
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