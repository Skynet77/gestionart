package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;
@Repository
public class InventarioDaoImpl extends DaoImpl<Inventario> implements InventarioDao {

	@Override
	public String getCamposFiltrables() {
		return "codigo||nombre||descripcion";
	}

	@Override
	public void destroy(String id) {
		
	}

}
