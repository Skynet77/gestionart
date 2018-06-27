package com.pol.gestionart.dao;

import java.util.Calendar;
import java.util.List;

import com.pol.gestionart.bean.InventarioDetalle;
import com.pol.gestionart.entity.Inventario;


public interface InventarioDao extends Dao<Inventario>{

	List<Inventario> getList(Integer filaInicio, Integer filaFin, String sSearch, Calendar sSearch2);
	public Inventario getInventarioByProductoFecha(Long idProducto, Integer mess);
	List<InventarioDetalle> joinInventario(Long idProducto, Integer mess);
}
