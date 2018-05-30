package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.entity.Proveedor;



@Component
public class ProveedorConverter implements Converter<String, Proveedor> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProveedorDao proveedorDao;
	
	@Override
	public Proveedor convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			Proveedor object = proveedorDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
