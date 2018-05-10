package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Producto;



@Component
public class ProductoConverter implements Converter<String, Producto> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ProductoDao productoDao;
	
	@Override
	public Producto convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			Producto object = productoDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
