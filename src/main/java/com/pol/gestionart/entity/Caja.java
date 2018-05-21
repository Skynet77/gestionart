package com.pol.gestionart.entity;

import java.math.BigDecimal;
import java.sql.Date;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "caja_id_uk", columnNames = { "ruc" }) })
public class Caja extends GenericEntity{
	
	private static final String SECUENCIA = "caja_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;
	
	/*@Size(max = 10)
	private String Codigo;*/
	
	/*@Size(max = 10)
	private String tipoCaja;*/
	
	private Date fecha;
	
	@Size(max = 50)
	private String descripcion;
	
	private BigDecimal entrada;
	
	private BigDecimal salida;
	
	private BigDecimal saldo_actual;
	
	@ManyToOne
	@NotNull(message = "caja.usuario.notNull")
	@JoinColumn(foreignKey = @ForeignKey(name = "caja_usuario_fk"))
	private Usuario usuario;
	
	public Caja() {
		super();
	}

	public Caja(Long id, Date fecha, @Size(max = 50) String descripcion, BigDecimal entrada, BigDecimal salida,
			BigDecimal saldo_actual, @NotNull(message = "caja.usuario.notNull") Usuario usuario) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.entrada = entrada;
		this.salida = salida;
		this.saldo_actual = saldo_actual;
		this.usuario = usuario;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getSalida() {
		return salida;
	}

	public void setSalida(BigDecimal salida) {
		this.salida = salida;
	}

	public BigDecimal getSaldo_actual() {
		return saldo_actual;
	}

	public void setSaldo_actual(BigDecimal saldo_actual) {
		this.saldo_actual = saldo_actual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static String getSecuencia() {
		return SECUENCIA;
	}


	
}