package com.pol.gestionart.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.pol.gestionart.controller.form.VentaFormController;
import com.pol.gestionart.entity.VentaDetalle;

@Component
public class GeneralUtils {
	
	public static String COMPROBANTE_PATTERN = "00000";
	
	public enum CodigoMoneda {
		GUARANIES("1", "GS","Gs"), 
		DOLARES("2", "USD", "U$"), 
		EUROS("3", "EUR","EUR");

		private String codigo;
		private String abreviatura;
		private String descripcion;

		private CodigoMoneda(String codigo, String abreviatura,String descripcion) {
			this.codigo = codigo;
			this.abreviatura = abreviatura;
			this.descripcion = descripcion;
		}

		public static CodigoMoneda fromString(String value) {
			for (CodigoMoneda cm : CodigoMoneda.values()) {
				if (cm.abreviatura.equalsIgnoreCase(value)) {
					return cm;
				}
			}
			return null;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getAbreviatura() {
			return abreviatura;
		}

		public void setAbreviatura(String abreviatura) {
			this.abreviatura = abreviatura;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

	}
	
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
	
	public static boolean isAjax(HttpServletRequest request) {
		String requestedWithHeader = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestedWithHeader);
	}
	
	public static String formatMontoMonedaToString(BigDecimal monto, String moneda, String mostrarMoneda) {
		String montoFormateado = "0";
		String formato = "";
		DecimalFormat nf = (DecimalFormat) DecimalFormat.getInstance(new Locale("es", "ES"));
		if (monto != null) {
			if ("S".equalsIgnoreCase(mostrarMoneda)) {
				if (moneda.equalsIgnoreCase(CodigoMoneda.GUARANIES.getAbreviatura()) || "".equalsIgnoreCase(moneda)) {
					formato = "###,### " + CodigoMoneda.GUARANIES.getDescripcion();
				} else {
					formato = "###,##0.00 " + CodigoMoneda.fromString(moneda).getDescripcion();
				}
			} else {
				if (moneda.equalsIgnoreCase(CodigoMoneda.GUARANIES.getAbreviatura()) || "".equalsIgnoreCase(moneda)) {
					formato = "###,### ";
				} else {
					formato = "###,##0.00 ";
				}
			}
			nf.applyPattern(formato);
			montoFormateado = nf.format(monto);
		}
		return montoFormateado;
	}
}
