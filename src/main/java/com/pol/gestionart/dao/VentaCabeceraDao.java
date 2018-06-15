package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaDetalle;

public interface VentaCabeceraDao extends Dao<VentaCabecera>{
	public List<VentaDetalle> getDetalleByIdCab(Long idCabecera);
}
