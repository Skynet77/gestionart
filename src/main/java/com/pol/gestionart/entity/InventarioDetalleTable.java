package com.pol.gestionart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table
public class InventarioDetalleTable extends GenericEntity{
private static final String SECUENCIA = "inventario_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	private String fecha;
	private String comprobante;
	private String operacion;
	private String proveedorCliente;
	private Integer cantidad;
	private Long idProducto;
	private int mes;
	public InventarioDetalleTable(String fecha, String comprobante, String operacion, String proveedorCliente, String nombre,
			String apellido) {
		super();
		this.fecha = fecha;
		this.comprobante = comprobante;
		this.operacion = operacion;
		this.proveedorCliente = proveedorCliente;
	}
	
	public InventarioDetalleTable(){
		
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
	
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	@Override
	public String toString() {
		return "InventarioDetalle [fecha=" + fecha + ", comprobante=" + comprobante + ", operacion=" + operacion
				+ ", proveedorCliente=" + proveedorCliente + "]";
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
	
	
}
