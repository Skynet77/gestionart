package com.pol.gestionart.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.pol.gestionart.entity.Familia;


@Component
public class FamiliaConverter implements Converter<String, Familia> {

	@Override
	public Familia convert(String idStr) {

		Familia familia = new Familia();
		Long id = Long.parseLong(idStr);
		familia.setId(id);
		return familia;
	}

}
