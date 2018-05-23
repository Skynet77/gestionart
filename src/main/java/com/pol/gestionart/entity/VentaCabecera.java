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

	//@NotNull
	private String nroComprobante;

	@ManyToOne
	//@NotNull(message = "ventaCabecera.cliente.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "ventaCabecera_cliente_fk"))
	private Cliente cliente;

	@NotNull
	@Size(max = 20)
	private String fechaEmision;

	@NotNull
	private BigDecimal montoTotal;


	@NotNull
	private BigDecimal subTotal;


	//@NotNull
	private BigDecimal iva;

	private String estado;

	public VentaCabecera(Long idVenta, @NotNull String nroComprobante, @NotNull String ruc,
			@NotNull(message = "ventaCabecera.cliente.notNull") Cliente cliente,
			@NotNull @Size(max = 20) String fechaEmision, @NotNull BigDecimal montoTotal, @NotNull BigDecimal subTotal,
			@NotNull BigDecimal iva, String estado) {
		super();
		this.idVenta = idVenta;
		this.nroComprobante = nroComprobante;
		this.cliente = cliente;
		this.fechaEmision = fechaEmision;
		this.montoTotal = montoTotal;
		this.subTotal = subTotal;
		this.iva = iva;
		this.estado = estado;
	}


	public VentaCabecera() {
		super();
	}

	public enum Estado{
		CONFIRMADO,
		PENDIENTE,
		INICIADA;
	}

	public Long getIdVenta() {
		return idVenta;
	}


	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}


	public String getNroComprobante() {
		return nroComprobante;
	}


	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}

	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public String getFechaEmision() {
		return fechaEmision;
	}


	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}


	public BigDecimal getMontoTotal() {
		return montoTotal;
	}


	public void setMontoTotal(String montoTotal) {
		if( montoTotal.contains(".")){
			String monto = montoTotal.replace(".", ""); //primero reemplazamos todos puntos por nada, por vacio
			//monto = monto.replace(",", ".");//segundo reemplazamos todas las comas por puntos
			this.montoTotal = new BigDecimal(monto);
		}else{
			this.montoTotal = new BigDecimal(montoTotal);
		}
		
//		this.montoTotal = montoTotal;
	}
	
	public void setMontoTotalBigDecimal(BigDecimal montoTotal) {
		
			this.montoTotal = montoTotal;
		
		
//		this.montoTotal = montoTotal;
	}


	public BigDecimal getSubTotal() {
		return subTotal;
	}


	public void setSubTotal(String subTotal) {
		if(subTotal.contains(".")){
			String monto = subTotal.replace(".", ""); //primero reemplazamos todos puntos por nada, por vacio
			monto = monto.replace(",", ".");//segundo reemplazamos todas las comas por puntos
			this.subTotal = new BigDecimal(monto);
		}else{
			this.subTotal = new BigDecimal(subTotal);
		}
		
//		this.subTotal = subTotal;
	}
	
	public void setSubTotalBigDecimal(BigDecimal subTotal) {
		
			this.subTotal = subTotal;
		
		
//		this.subTotal = subTotal;
	}


	public BigDecimal getIva() {
		return iva;
	}


	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public Long getId() {
		return this.idVenta;
	}


	@Override
	public void setId(Long id) {
		this.idVenta = id;

	}

	@Override
	public String toString() {
		return "VentaCabecera [idVenta=" + idVenta + ", nroComprobante=" + nroComprobante + ", cliente=" + cliente
				+ ", fechaEmision=" + fechaEmision + ", montoTotal=" + montoTotal + ", subTotal=" + subTotal + ", iva="
				+ iva + ", estado=" + estado + "]";
	}

}
