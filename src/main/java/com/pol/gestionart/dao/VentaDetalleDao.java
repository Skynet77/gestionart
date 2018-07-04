package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.entity.VentaDetalle;

public interface VentaDetalleDao extends Dao<VentaDetalle>{
	List<VentaDetalle> getListDetalle(Long idCabeceraDetalle);
	Long countVentaCabecera();
}
