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

import com.pol.gestionart.controller.list.ProductoListController;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.dao.Dao;
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
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", productoList.getColumnas());
		map.addAttribute("columnasStr", productoList.getColumnasStr(null));
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("tituloFormulario", "Registrar Producto");
		map.addAttribute("producto", new Producto());
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
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
			return edit(map, obj.getId(),obj);
		} else if ("delete".equals(accion)) {
			return editarEstado(map, id_objeto);

		}
		return getTemplatePath();

	}
	
	// función para actualizar el estado a inactivo en vez de eliminarlo
		private String editarEstado(ModelMap map, Long id_objeto) {
			try {
				Producto obj = getDao().find(id_objeto);
				if (obj == null) {
					map.addAttribute("error", "No se han encontrado registros con el id: " + id_objeto);
				} else {
					obj.setEstado("I");
					getDao().createOrUpdate(obj);
					map.addAttribute(getNombreObjeto(), obj);
					map.addAttribute("msgExito", "Registro eliminado correctamente");
					logger.info("registro eliminado");
				}
			} catch (Exception ex){
				map.addAttribute("error", "Error al eliminar el registro. " + ex.getMessage());
			}
			agregarValoresAdicionales(map);
			logger.info("Registro retorna {}", getTemplatePath());
			return getTemplatePath();
		}
	
	@RequestMapping(value = "buscar", method = RequestMethod.POST)
	public String editar_listado(ModelMap map, 
			@RequestParam(value = "id_producto", required = false) Long idProducto) {
		try {
			Producto producto = null;
			if (idProducto != null ) {
					producto = productoDao.find(idProducto);
					logger.info("Producto encontrado{}", producto);
			}
			agregarValoresAdicionales(map);
			map.addAttribute("producto", producto);
			map.addAttribute("tituloFormulario", "Editar Producto");
			map.addAttribute("accion", "editar");

		} catch (Exception ex) {
			Producto producto = new Producto();
			producto.setIdProducto(null);
			map.addAttribute("error", getErrorFromException(ex));
			return getTemplatePath();
			
		}
		return "producto/producto_form";

	}
	
	@Override
	public Dao<Producto> getDao() {
		return productoDao;
	}

}