package com.pol.gestionart.entity;

import javax.persistence.Entity;
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

import com.pol.gestionart.bean.GenericEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "compra_detalle_uk", columnNames = { "id_compra", "id_producto" }) })

public class CompraDetalle extends GenericEntity {
private static final String SECUENCIA = "compraDetalle_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@ManyToOne
	@NotNull(message = "compraDetalle.compraCabecera.notNull")
	//@JoinColumn(foreignKey = @ForeignKey(name = "compraDetalle_compraCabecera_fk"))
	private CompraCabecera compraCabecera;
	
	@ManyToOne
	@NotNull(message = "compraDetalle.producto.notNull")
	//@JoinColumn(foreignKey = @ForeignKey(name = "compraDetalle_producto_fk"))
	private Producto producto;
	
	@Size(max = 50)
	@NotNull(message = "compraDetalle.descripcion.notNull")
	private String descripcion;
	
	@Size(max = 50)
	@NotNull(message = "compraDetalle.precioUnitario.notNull")
	private int precioUnitario;
	
	@Size(max = 50)
	@NotNull(message = "compraDetalle.iva.notNull")
	private int iva;
	
	@Size(max = 50)
	@NotNull(message = "compraDetalle.cantidad.notNull")
	private int cantidad;
	
	public CompraDetalle() {
		super();
	}

	public CompraDetalle(Long id,
			@NotNull(message = "compraDetalle.compraCabecera.notNull") CompraCabecera compraCabecera,
			@NotNull(message = "compraDetalle.producto.notNull") Producto producto,
			@Size(max = 50) @NotNull(message = "compraDetalle.descripcion.notNull") String descripcion,
			@Size(max = 50) @NotNull(message = "compraDetalle.precioUnitario.notNull") int precioUnitario,
			@Size(max = 50) @NotNull(message = "compraDetalle.iva.notNull") int iva,
			@Size(max = 50) @NotNull(message = "compraDetalle.cantidad.notNull") int cantidad) {
		super();
		this.id = id;
		this.compraCabecera = compraCabecera;
		this.producto = producto;
		this.descripcion = descripcion;
		this.precioUnitario = precioUnitario;
		this.iva = iva;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(int precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
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

	@Override
	public String toString() {
		return "CompraDetalle [id=" + id + ", compraCabecera=" + compraCabecera + ", producto=" + producto
				+ ", descripcion=" + descripcion + ", precioUnitario=" + precioUnitario + ", iva=" + iva + ", cantidad="
				+ cantidad + "]";
	}

}
