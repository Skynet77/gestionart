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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "producto_codigo_uk", columnNames = { "codigo" }) })
public class Producto extends GenericEntity {
	
	private static final String SECUENCIA = "producto_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long idProducto;
	
	@NotBlank(message = "producto.codigo.notBlank")
	@NotNull(message = "producto.codigo.notNull")
	private String codigo;
	@NotNull
	private String tipoProducto; // P producto, M materia prima
	@Size(max = 100)
	private String descripcion;
	@Size(max = 50)
	private String marca;
	private String capacidad; //capacidad en litros o kg valores numericos
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;
	private BigDecimal iva;
	private int cantidad;
	private String estado;
	
	@ManyToOne
	@NotNull(message = "{producto.familia.notNull}")
	@JoinColumn(foreignKey = @ForeignKey(name = "producto_familia_fk"))
	private Familia familia;
	
	public Long getIdProducto() {
		return idProducto;
	}
	
	public Producto() {
		super();
	}

	public Producto(Long idProducto, String codigo, @NotNull String tipoProducto, @Size(max = 100) String descripcion,
			@Size(max = 50) String marca, String capacidad, BigDecimal precioCompra, BigDecimal precioVenta,
			BigDecimal iva, int cantidad, String estado) {
		super();
		this.idProducto = idProducto;
		this.codigo = codigo;
		this.tipoProducto = tipoProducto;
		this.descripcion = descripcion;
		this.marca = marca;
		this.capacidad = capacidad;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.iva = iva;
		this.cantidad = cantidad;
		this.estado = estado;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}
	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}
	public BigDecimal getIva() {
		return iva;
	}
	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}
	public static String getSecuencia() {
		return SECUENCIA;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		if(estado == null){
			this.estado = "I";
		}else{
			this.estado = estado;
		}
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public Long getId() {
		return idProducto;
	}

	@Override
	public void setId(Long id) {
		this.idProducto = idProducto;
		
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
}
