package com.pol.gestionart.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
//uniqueConstraints = { @UniqueConstraint(name = "compra_cabecera_idCompra_uk", columnNames = { "idCompra" }) }
public class CompraCabecera extends GenericEntity{
	
	private static final String SECUENCIA = "compraCabecera_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	
	@ManyToOne
	@NotNull(message = "compraCabecera.proveedor.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "compraCabecera_proveedor_fk"))
	private Proveedor proveedor;
	
	@Size(max = 20)
	@NotNull(message = "compraCabecera.nroFactura.notNull")
	private String nroFactura;
	
	@Size(max = 20)
	@NotNull(message = "compraCabecera.timbrado.notNull")
	private String timbrado;
	
	@Size(max = 20)
	@NotNull(message = "compraCabecera.fechaCompra.notNull")
	private String fechaCompra;
	
	@NotNull(message = "compraCabecera.monto.notNull")
	private BigDecimal monto;
	
	@NotNull
	private BigDecimal subTotal;
	
	@NotNull
	private BigDecimal iva;
	
	@Size(max = 10)
	@NotNull(message = "compraCabecera.estado.notNull")
	private String estado;
	
	/*@OneToMany
	private List<CompraDetalle> compraDetalle;*/

	public CompraCabecera() {
		super();
	}
	
	public CompraCabecera(Long id, @NotNull(message = "compraCabecera.proveedor.notNull") Proveedor proveedor,
			@Size(max = 20) @NotNull(message = "compraCabecera.nroFactura.notNull") String nroFactura,
			@Size(max = 20) @NotNull(message = "compraCabecera.timbrado.notNull") String timbrado,
			@Size(max = 20) @NotNull(message = "compraCabecera.fechaCompra.notNull") String fechaCompra,
			@NotNull(message = "compraCabecera.monto.notNull") BigDecimal monto, @NotNull BigDecimal subTotal,
			@NotNull BigDecimal iva,
			@Size(max = 10) @NotNull(message = "compraCabecera.estado.notNull") String estado) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.nroFactura = nroFactura;
		this.timbrado = timbrado;
		this.fechaCompra = fechaCompra;
		this.monto = monto;
		this.subTotal = subTotal;
		this.iva = iva;
		this.estado = estado;
	}


	public String getNroFactura() {
		return nroFactura;
	}


	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}




	public String getTimbrado() {
		return timbrado;
	}




	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}




	public String getFechaCompra() {
		return fechaCompra;
	}




	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}




	public BigDecimal getMonto() {
		return monto;
	}




	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	

	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	/*public List<CompraDetalle> getCompraDetalle() {
		return compraDetalle;
	}

	public void setCompraDetalle(List<CompraDetalle> compraDetalle) {
		this.compraDetalle = compraDetalle;
	}*/

	@Override
	public String toString() {
		return "CompraCabecera [id=" + id + ", proveedor=" + proveedor + ", nroFactura=" + nroFactura + ", timbrado="
				+ timbrado + ", fechaCompra=" + fechaCompra + ", monto=" + monto + ", subTotal=" + subTotal + ", iva="
				+ iva + ", estado=" + estado + "]";
	}

	
	
	

}
