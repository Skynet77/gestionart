package com.pol.gestionart.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "inventario_producto_uk", columnNames = { "fechames", "producto_idproducto" }) })
public class Inventario extends GenericEntity {
	private static final String SECUENCIA = "inventario_id_seq";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date fechaMes;
	
	private int stockInicial;
	
	private int entrada;
	
	private int salida;
	
	private int actual;
	
	@ManyToOne
	@NotNull(message = "inventario.producto.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "inventario_producto_fk"))
	private Producto producto;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	public Inventario(){};
	
	public Inventario(Long id, Date fechaActual, int stockInicial, int entrada, int salida, int actual,
			@NotNull(message = "inventario.producto.notNull") Producto producto) {
		super();
		this.id = id;
		this.fechaMes = fechaActual;
		this.stockInicial = stockInicial;
		this.entrada = entrada;
		this.salida = salida;
		this.actual = actual;
		this.producto = producto;
	}

	public Date getFechaMes() {
		return fechaMes;
	}

	public void setFechaMes(Date fechaActual) {
		this.fechaMes = fechaActual;
	}

	public int getStockInicial() {
		return stockInicial;
	}

	public void setStockInicial(int stockInicial) {
		this.stockInicial = stockInicial;
	}

	public int getEntrada() {
		return entrada;
	}

	public void setEntrada(int entrada) {
		this.entrada = entrada;
	}

	public int getSalida() {
		return salida;
	}

	public void setSalida(int salida) {
		this.salida = salida;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", fechaActual=" + fechaMes + ", stockInicial=" + stockInicial + ", entrada="
				+ entrada + ", salida=" + salida + ", actual=" + actual + ", producto=" + producto + "]";
	}

	
	
	
}

