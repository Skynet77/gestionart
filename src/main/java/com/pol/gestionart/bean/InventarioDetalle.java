package com.pol.gestionart.bean;

import java.util.Date;

import com.pol.gestionart.util.GeneralUtils;

public class InventarioDetalle implements Comparable<InventarioDetalle> {

	private String fecha;
	private String comprobante;
	private String operacion;
	private String proveedorCliente;
	private Integer cantidad;
	public InventarioDetalle(String fecha, String comprobante, String operacion, String proveedorCliente, String nombre,
			String apellido) {
		super();
		this.fecha = fecha;
		this.comprobante = comprobante;
		this.operacion = operacion;
		this.proveedorCliente = proveedorCliente;
	}
	
	public InventarioDetalle(){
		
	}
	
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getComprobante() {
		return comprobante;
	}
	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getProveedorCliente() {
		return proveedorCliente;
	}
	public void setProveedorCliente(String proveedorCliente) {
		this.proveedorCliente = proveedorCliente;
	}
	@Override
	public String toString() {
		return "InventarioDetalle [fecha=" + fecha + ", comprobante=" + comprobante + ", operacion=" + operacion
				+ ", proveedorCliente=" + proveedorCliente + "]";
	}

	@Override
	public int compareTo(InventarioDetalle inventario) {
		Date d1 = GeneralUtils.getDateHours(fecha);
		Date d2 = GeneralUtils.getDateHours(inventario.getFecha());
		if(d1.before(d2)) {
			return -1;
		}else if(d1.after(d2)) {
			return 1;
		}
		return 0;
	}
	
	
}
