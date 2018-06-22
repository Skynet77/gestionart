package com.pol.gestionart.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
public class ReporteCaja extends GenericEntity {
	private static final String SECUENCIA = "caja_reporte_id_seq";

	private BigDecimal totalIngreso;
	private BigDecimal totalEgreso;
	private BigDecimal totalActual;
	private BigDecimal apertura;
	private String fecha;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	public ReporteCaja() {
		super();
	}
	
	
	public ReporteCaja(BigDecimal totalIngreso, BigDecimal totalEgreso, BigDecimal totalActual,
			BigDecimal apertura, String fecha, Long id) {
		super();
		this.totalIngreso = totalIngreso;
		this.totalEgreso = totalEgreso;
		this.totalActual = totalActual;
		this.apertura = apertura;
		this.fecha = fecha;
		this.id = id;
	}

	public BigDecimal getTotalIngreso() {
		String monto = this.totalIngreso.toString().replace(".", "");
		return totalIngreso;
	}
	
	public void setTotalIngreso(BigDecimal totalIngreso) {
		this.totalIngreso = totalIngreso;
	}
	public BigDecimal getTotalEgreso() {
		String monto = this.totalEgreso.toString().replace(".", "");
		return totalEgreso;
	}
	public void setTotalEgreso(BigDecimal totalEgreso) {
		this.totalEgreso = totalEgreso;
	}
	public BigDecimal getTotalActual() {
		String monto = this.totalActual.toString().replace(".", "");
		return totalActual;
	}
	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public BigDecimal getTotalIngresoBigDecimal() {
		return totalIngreso;
	}
	
	public BigDecimal getTotalActualBigDecimal() {
		return totalActual;
	}
	
	public BigDecimal getTotalEgresoBigDecimal() {
		return totalEgreso;
	}
	
	
	public BigDecimal getApertura() {
		return apertura;
	}
	public void setApertura(BigDecimal apertura) {
		this.apertura = apertura;
	}
	
	@Override
	public String toString() {
		return "ReporteCaja [totalIngreso=" + totalIngreso + ", totalEgreso=" + totalEgreso + ", totalActual="
				+ totalActual + ", apertura=" + apertura + ", fecha=" + fecha + ", id=" + id + "]";
	}


	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
	
	
	
}
