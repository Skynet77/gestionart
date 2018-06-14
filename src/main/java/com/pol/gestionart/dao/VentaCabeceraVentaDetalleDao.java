package com.pol.gestionart.dao;

import com.pol.gestionart.entity.Caja;
import com.pol.gestionart.entity.VentaCabeceraVentaDetalle;


public interface VentaCabeceraVentaDetalleDao extends Dao<VentaCabeceraVentaDetalle>{
	public VentaCabeceraVentaDetalle getCajaVentaByIdCaja(Long idCaja);
}
