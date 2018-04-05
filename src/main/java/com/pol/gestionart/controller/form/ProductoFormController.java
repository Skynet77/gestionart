package com.pol.gestionart.controller.form;

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

import com.pol.gestionart.controller.list.ProductoListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.entity.Producto;

@Controller
@Scope("request")
@RequestMapping("producto")
public class ProductoFormController extends FormController<Producto> {
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private ProductoListController productoList;

	@Override
	public String getTemplatePath() {
		return "producto/producto_index";
	}

	@Override
	public String getNombreObjeto() {
		return "producto";
	}

	@Override
	public Producto getNuevaInstancia() {
		return new Producto();
	}
	
	@Override
	public void agregarValoresAdicionales(ModelMap map){
		map.addAttribute("producto", productoList.getColumnas());
		map.addAttribute("columnaStr", productoList.getColumnas());
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("producto", new Producto());
		
	}
	
	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid Producto obj,
			BindingResult bindingResult,
			@RequestParam(required = false) String accion,
			@RequestParam(value = "id_objeto", required = false) Long id_objeto) {
		if (StringUtils.equals(accion, "save")) {
			return guardar(map, obj, bindingResult);
		} else if (StringUtils.equals(accion, "edit")) {
			logger.info("OBJETO PROCESO {}", obj);
			return edit(map, obj.getId());
		} else if (id_objeto != null) {
			return delete(map, id_objeto);

		}
		return getTemplatePath();

	}
	
	@Override
	public Dao<Producto> getDao() {
		return null;
	}

}
