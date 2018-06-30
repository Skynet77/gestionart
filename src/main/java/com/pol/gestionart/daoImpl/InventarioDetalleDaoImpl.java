package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.InventarioDetalleDao;
import com.pol.gestionart.entity.InventarioDetalleTable;
@Repository
public class InventarioDetalleDaoImpl extends DaoImpl<InventarioDetalleTable> implements InventarioDetalleDao {

	@Override
	public String getCamposFiltrables() {
		return "producto.codigo||producto.descripcion";
	}

	@Override
	public void destroy(String id) {
		
	}
	
}
