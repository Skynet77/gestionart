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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
public class CompraDetalle extends GenericEntity {
private static final String SECUENCIA = "compraDetalle_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@ManyToOne
	@NotNull(message = "compraDetalle.compraCabecera.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "compraDetalle_compraCabecera_fk"))
	private CompraCabecera compraCabecera;
	
	
	@ManyToOne
	@NotNull(message = "compraDetalle.producto.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "compraDetalle_producto_fk"))
	private Producto producto;
	

	@NotNull(message = "compraDetalle.precioUnitario.notNull")
	private BigDecimal precioUnitario;
	
	@NotNull
	private BigDecimal precioTotal;
	
	@NotNull(message = "compraDetalle.cantidad.notNull")
	private int cantidad;
	
	public CompraDetalle() {
		super();
	}


	public CompraDetalle(Long id,
			@NotNull(message = "compraDetalle.compraCabecera.notNull") CompraCabecera compraCabecera,
			@NotNull(message = "compraDetalle.producto.notNull") Producto producto,
			@NotNull(message = "compraDetalle.precioUnitario.notNull") BigDecimal precioUnitario, @NotNull BigDecimal precioTotal,
			@NotNull(message = "compraDetalle.cantidad.notNull") int cantidad) {
		super();
		this.id = id;
		this.compraCabecera = compraCabecera;
		this.producto = producto;
		this.precioUnitario = precioUnitario;
		this.precioTotal = precioTotal;
		this.cantidad = cantidad;
	}


	public CompraCabecera getCompraCabecera() {
		return compraCabecera;
	}

	public void setCompraCabecera(CompraCabecera compraCabecera) {
		this.compraCabecera = compraCabecera;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
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


	public static String getSecuencia() {
		return SECUENCIA;
	}


	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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
		return "CompraDetalle [id=" + id + ", compraCabecera=" + compraCabecera + ", producto=" + producto
				+ ", precioUnitario=" + precioUnitario + ", precioTotal=" + precioTotal + ", cantidad=" + cantidad
				+ "]";
	}

}
