package com.pol.gestionart.bean;

import java.math.BigDecimal;

import com.pol.gestionart.entity.Caja;

public class ReporteCaja {

	private Caja caja;
	private BigDecimal totalIngreso;
	private BigDecimal totalEgreso;
	private BigDecimal totalActual;
	private String fecha;
	public ReporteCaja() {
		super();
	}
	public Caja getCaja() {
		return caja;
	}
	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	public BigDecimal getTotalIngreso() {
		return totalIngreso;
	}
	public void setTotalIngreso(BigDecimal totalIngreso) {
		this.totalIngreso = totalIngreso;
	}
	public BigDecimal getTotalEgreso() {
		return totalEgreso;
	}
	public void setTotalEgreso(BigDecimal totalEgreso) {
		this.totalEgreso = totalEgreso;
	}
	public BigDecimal getTotalActual() {
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
	@Override
	public String toString() {
		return "ReporteCaja [caja=" + caja + ", totalIngreso=" + totalIngreso + ", totalEgreso=" + totalEgreso
				+ ", totalActual=" + totalActual + ", fecha=" + fecha + "]";
	}
	
	
	
}
