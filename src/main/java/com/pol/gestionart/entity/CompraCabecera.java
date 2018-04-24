package com.pol.gestionart.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@Size(max = 50)
	@NotNull(message = "compraCabecera.idUsuario.notNull")
	private String idUsuario;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.idProveedor.notNull")
	private String idProveedor;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.nroFactura.notNull")
	private String nroFactura;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.timbrado.notNull")
	private String timbrado;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.fechaCompra.notNull")
	private String fechaCompra;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.monto.notNull")
	private String monto;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.tipoCompra.notNull")
	private String tipoCompra;
	@Size(max = 50)
	@NotNull(message = "compraCabecera.estado.notNull")
	private String estado;
	
	@OneToMany
	private List<CompraDetalle> compraDetalle;

	public CompraCabecera() {
		super();
	}

	public CompraCabecera(Long id,
			@Size(max = 50) @NotNull(message = "compraCabecera.idUsuario.notNull") String idUsuario,
			@Size(max = 50) @NotNull(message = "compraCabecera.idProveedor.notNull") String idProveedor,
			@Size(max = 50) @NotNull(message = "compraCabecera.nroFactura.notNull") String nroFactura,
			@Size(max = 50) @NotNull(message = "compraCabecera.timbrado.notNull") String timbrado,
			@Size(max = 50) @NotNull(message = "compraCabecera.fechaCompra.notNull") String fechaCompra,
			@Size(max = 50) @NotNull(message = "compraCabecera.monto.notNull") String monto,
			@Size(max = 50) @NotNull(message = "compraCabecera.tipoCompra.notNull") String tipoCompra,
			@Size(max = 50) @NotNull(message = "compraCabecera.estado.notNull") String estado,
			List<CompraDetalle> compraDetalle) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idProveedor = idProveedor;
		this.nroFactura = nroFactura;
		this.timbrado = timbrado;
		this.fechaCompra = fechaCompra;
		this.monto = monto;
		this.tipoCompra = tipoCompra;
		this.estado = estado;
		this.compraDetalle = compraDetalle;
	}








	public String getIdUsuario() {
		return idUsuario;
	}




	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}




	public String getIdProveedor() {
		return idProveedor;
	}




	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
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




	public String getMonto() {
		return monto;
	}




	public void setMonto(String monto) {
		this.monto = monto;
	}




	public String getTipoCompra() {
		return tipoCompra;
	}




	public void setTipoCompra(String tipoCompra) {
		this.tipoCompra = tipoCompra;
	}




	public String getEstado() {
		return estado;
	}




	public void setEstado(String estado) {
		this.estado = estado;
	}




	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	public List<CompraDetalle> getCompraDetalle() {
		return compraDetalle;
	}

	public void setCompraDetalle(List<CompraDetalle> compraDetalle) {
		this.compraDetalle = compraDetalle;
	}

	@Override
	public String toString() {
		return "CompraCabecera [id=" + id + ", idUsuario=" + idUsuario + ", idProveedor=" + idProveedor
				+ ", nroFactura=" + nroFactura + ", timbrado=" + timbrado + ", fechaCompra=" + fechaCompra + ", monto="
				+ monto + ", tipoCompra=" + tipoCompra + ", estado=" + estado + ", compraDetalle=" + compraDetalle
				+ "]";
	}

	
	
	

}
