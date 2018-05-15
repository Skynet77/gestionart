package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.entity.Cliente;



@Component
public class ClienteConverter implements Converter<String, Cliente> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ClienteDao clienteDao;
	
	@Override
	public Cliente convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			Cliente object = clienteDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
