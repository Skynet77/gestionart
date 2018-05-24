package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.entity.CompraDetalle;

public interface CompraDetalleDao extends Dao<CompraDetalle> {
	List<CompraDetalle> getListDetalle(Long idCabeceraDetalle);
}
