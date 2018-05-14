package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.entity.CompraCabecera;



@Component
public class CompraCabeceraConverter implements Converter<String, CompraCabecera> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CompraCabeceraDao compraCabeceraDao;
	
	@Override
	public CompraCabecera convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			CompraCabecera object = compraCabeceraDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
