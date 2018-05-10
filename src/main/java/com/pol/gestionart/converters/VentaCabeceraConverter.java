package com.pol.gestionart.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.entity.VentaCabecera;



@Component
public class VentaCabeceraConverter implements Converter<String, VentaCabecera> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	VentaCabeceraDao ventaCabeceraDao;
	
	@Override
	public VentaCabecera convert(String idStr) {
		try {
			Long objId = Long.valueOf(idStr);
			VentaCabecera object =  ventaCabeceraDao.find(objId);
			return object;
		} catch (NumberFormatException nfe) {
			return null;
		} catch (Exception exc) {
			logger.error("Error al convertir objeto: {}", exc, exc.getMessage());
			return null;
		}

	}

}
