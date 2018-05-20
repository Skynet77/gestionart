package com.pol.gestionart.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.pol.gestionart.controller.form.VentaFormController;
import com.pol.gestionart.entity.VentaDetalle;

@Component
public class GeneralUtils {
	
	public static String COMPROBANTE_PATTERN = "00000";
	
	public static Map<String, VentaDetalle> mapSerializeVentaDetalleOrUpdate(HttpSession session, 	
			VentaDetalle ventaDet){
		Map<String, VentaDetalle> mapSerializeCdas = new HashMap<>();
		if(session.getAttribute(VentaFormController.MAP_DETALLE) == null){
				String keyMap = UUID.randomUUID().toString();
				mapSerializeCdas.put(keyMap, ventaDet);

			return mapSerializeCdas;
		}else{
			mapSerializeCdas = (Map<String, VentaDetalle>) session.getAttribute(VentaFormController.MAP_DETALLE);
				String keyMap = UUID.randomUUID().toString();
				mapSerializeCdas.put(keyMap, ventaDet);
			return mapSerializeCdas;
		}
	}
	
	public static String formatoComprobante(double value ) {
		
	      DecimalFormat myFormatter = new DecimalFormat(COMPROBANTE_PATTERN);
	      String output = myFormatter.format(value);
	      
	      return output;
	   }
	public static String getStringFromDate(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
}
