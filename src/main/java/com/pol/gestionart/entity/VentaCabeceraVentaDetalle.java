package com.pol.gestionart.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pol.gestionart.main.GenericEntity;


@Entity
@Table//(uniqueConstraints = { @UniqueConstraint(name = "ventaCabecera_id_uk", columnNames = { "venta_cab_id" }) })
public class VentaCabeceraVentaDetalle extends GenericEntity{

	private static final String SECUENCIA = "ventaCab_ventaDet_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "cabeceraDetalle_cabecera_fk"))
	private VentaCabecera ventaCabecera;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "cabeceraDetalle_detalle_fk"))
	private VentaDetalle ventaDetalle;

	public VentaCabeceraVentaDetalle(Long id, VentaCabecera ventaCabecera, VentaDetalle ventaDetalle) {
		super();
		this.id = id;
		this.ventaCabecera = ventaCabecera;
		this.ventaDetalle = ventaDetalle;
	}

	public VentaCabeceraVentaDetalle() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VentaCabecera getVentaCabecera() {
		return ventaCabecera;
	}

	public void setVentaCabecera(VentaCabecera ventaCabecera) {
		this.ventaCabecera = ventaCabecera;
	}

	public VentaDetalle getVentaDetalle() {
		return ventaDetalle;
	}

	public void setVentaDetalle(VentaDetalle ventaDetalle) {
		this.ventaDetalle = ventaDetalle;
	}

	@Override
	public String toString() {
		return "VentaCabeceraVentaDetalle [id=" + id + ", ventaCabecera=" + ventaCabecera + ", ventaDetalle="
				+ ventaDetalle + "]";
	}
	
	
}
