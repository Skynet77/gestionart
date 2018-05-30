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

	private String nroFactura;

	private String timbrado;

	private String fechaCompra;

	private BigDecimal total;

	private BigDecimal subTotal;

	private String nroComprobante;
	
	
	public CompraCabecera() {
		super();
	}

	public CompraCabecera(Long id, @NotNull(message = "compraCabecera.proveedor.notNull") Proveedor proveedor,
			String nroFactura, String timbrado, String fechaCompra, BigDecimal monto, BigDecimal subTotal,
			String nroComprobante) {
		super();
		this.id = id;
		this.proveedor = proveedor;
		this.nroFactura = nroFactura;
		this.timbrado = timbrado;
		this.fechaCompra = fechaCompra;
		this.total = monto;
		this.subTotal = subTotal;
		this.nroComprobante = nroComprobante;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotalBigDecimal(BigDecimal monto) {
        this.total = monto;
    }

    public void setTotal(String monto) {
        if(monto.contains(".")){
            String monto1 = monto.replace(".", ""); //primero reemplazamos todos puntos por nada, por vacio
            monto1 = monto1.replace(",", ".");//segundo reemplazamos todas las comas por puntos
            this.total = new BigDecimal(monto1);
        }else{
            this.total = new BigDecimal(monto);
        }
    }

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotalBigDecimal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public void setSubTotal(String subTotal) {
        if(subTotal.contains(".")){
            String monto = subTotal.replace(".", ""); //primero reemplazamos todos puntos por nada, por vacio
            monto = monto.replace(",", ".");//segundo reemplazamos todas las comas por puntos
            this.subTotal = new BigDecimal(monto);
        }else{
            this.subTotal = new BigDecimal(subTotal);
        }
    }

	public String getNroComprobante() {
		return nroComprobante;
	}

	public void setNroComprobante(String nroComprobante) {
		this.nroComprobante = nroComprobante;
	}

	@Override
	public String toString() {
		return "CompraCabecera [id=" + id + ", proveedor=" + proveedor + ", nroFactura=" + nroFactura + ", timbrado="
				+ timbrado + ", fechaCompra=" + fechaCompra + ", monto=" + total + ", subTotal=" + subTotal
				+ ", nroComprobante=" + nroComprobante + "]";
	}





}
