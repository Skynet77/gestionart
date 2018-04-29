package com.pol.gestionart.controller.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.controller.list.VentaCabeceraListController;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.VentaCabeceraDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.entity.VentaCabecera;
import com.pol.gestionart.entity.VentaCabecera;



@Controller
@Scope("request")
@RequestMapping("venta")
public class VentaFormController extends FormController<VentaCabecera> {

	@Autowired
	private VentaCabeceraDao ventaCabeceraDao;
	

	@Autowired
	private VentaCabeceraListController productoList;

	
	@Override
	public String getTemplatePath() {
		return "venta/venta_index";
	}

	@Override
	public String getNombreObjeto() {
		return "venta";
	}

	@Override
	public VentaCabecera getNuevaInstancia() {
		return new VentaCabecera();
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", productoList.getColumnas());
		map.addAttribute("columnasStr", productoList.getColumnasStr(null));
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("tituloFormulario", "Registrar Venta");
		map.addAttribute("producto", new VentaCabecera());
		map.addAttribute("accion", "guardar");
		map.addAttribute("ventaList", getDao().findEntities(true,-1,-1, "VentaCabecera"));
		map.addAttribute("titlePage", "Registro de Venta");
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid VentaCabecera obj,
			BindingResult bindingResult,
			@RequestParam(required = false) String accion,
			@RequestParam(value = "id_objeto", required = false) Long id_objeto) {
		if (StringUtils.equals(accion, "save")) {
			return guardar(map, obj, bindingResult);
		} else if (StringUtils.equals(accion, "edit")) {
			logger.info("OBJETO PROCESO {}", obj);
			return edit(map, obj.getId(),obj);
		} else if ("delete".equals(accion)) {
			//return editarEstado(map, id_objeto);

		}
		return getTemplatePath();

	}
	
	
	
	@Override
	public Dao<VentaCabecera> getDao() {
		return ventaCabeceraDao;
	}

}