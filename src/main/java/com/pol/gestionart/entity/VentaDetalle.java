package com.pol.gestionart.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pol.gestionart.main.GenericEntity;


@Entity
@Table//(uniqueConstraints = { @UniqueConstraint(name = "ventaCabecera_id_uk", columnNames = { "venta_cab_id" }) })
public class VentaDetalle extends GenericEntity{
	
	private static final String SECUENCIA = "ventaDet_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	
	@ManyToOne
	@NotNull(message = "ventaDetalle.ventaCabecera.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "ventaDetalle_ventaCabecera_fk"))
	private VentaCabecera ventaCabecera; 
	
	@ManyToOne
	@NotNull(message = "ventaDetalle.producto.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "ventaDetalle_producto_fk"))
	private Producto producto;

	@NotNull
	private int cantidad;


	@NotNull
	private BigDecimal precioUnitario;
	
	
	@NotNull
	private BigDecimal precioTotal;


	public VentaDetalle() {
		super();
	}

	public VentaDetalle(Long id, @NotNull(message = "ventaDetalle.ventaCabecera.notNull") VentaCabecera ventaCabecera,
			@NotNull(message = "ventaDetalle.producto.notNull") Producto producto, @NotNull int cantidad,
			@NotNull BigDecimal precioUnitario, @NotNull BigDecimal precioTotal) {
		super();
		this.id = id;
		this.ventaCabecera = ventaCabecera;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.precioTotal = precioTotal;
	}



	@Override
	public Long getId() {
		return id;
	}


	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public VentaCabecera getVentaCabecera() {
		return ventaCabecera;
	}

	public void setVentaCabecera(VentaCabecera ventaCabecera) {
		this.ventaCabecera = ventaCabecera;
	}

	@Override
	public String toString() {
		return "VentaDetalle [id=" + id + ", ventaCabecera=" + ventaCabecera + ", producto=" + producto + ", cantidad="
				+ cantidad + ", precioUnitario=" + precioUnitario + ", precioTotal=" + precioTotal + "]";
	}

	

}