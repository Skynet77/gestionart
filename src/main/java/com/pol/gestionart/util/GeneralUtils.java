package com.pol.gestionart.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.pol.gestionart.controller.form.VentaFormController;
import com.pol.gestionart.entity.VentaDetalle;

@Component
public class GeneralUtils {
	
	
	public static Map<String, VentaDetalle> mapSerializeVentaDetalleOrUpdate(HttpSession session, List<VentaDetalle> listDetalle){
		if(session.getAttribute(VentaFormController.LISTA_DETALLE) == null){
			Map<String, VentaDetalle> mapSerializeCdas = new HashMap<>();
			for(VentaDetalle c : listDetalle){
				String keyMap = UUID.randomUUID().toString();
				mapSerializeCdas.put(keyMap, c);
			}

			return mapSerializeCdas;
		}else{
			Map<String, VentaDetalle> mapCdas = (Map<String, VentaDetalle>) session.getAttribute(VentaFormController.LISTA_DETALLE);

			/*for(Entry<String, VentaDetalle> c : mapCdas.entrySet()){
				for (VentaDetalle cda : listDetalle) {					
					
				}
			}*/
			return mapCdas;
		}
	}
}
