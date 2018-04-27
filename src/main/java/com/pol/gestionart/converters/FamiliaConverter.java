package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.FamiliaDao;
import com.pol.gestionart.entity.Familia;



@Component
public class FamiliaConverter implements Converter<String, Familia> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	FamiliaDao familiaDao;
	
	@Override
	public Familia convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			Familia object = familiaDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
