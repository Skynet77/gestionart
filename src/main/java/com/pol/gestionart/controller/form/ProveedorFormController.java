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

import com.pol.gestionart.controller.list.ProveedorListController;
import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Proveedor;
import com.pol.gestionart.entity.Proveedor;

@Controller
@Scope("request")
@RequestMapping("proveedor")
public class ProveedorFormController extends FormController<Proveedor> {

	@Autowired
	private ProveedorDao proveedorDao;
	

	@Autowired
	private ProveedorListController proveedorList;

	
	@Override
	public String getTemplatePath() {
		return "proveedor/proveedor_index";
	}

	@Override
	public String getNombreObjeto() {
		return "proveedor";
	}

	@Override
	public Proveedor getNuevaInstancia() {
		return new Proveedor();
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", proveedorList.getColumnas());
		map.addAttribute("columnasStr", proveedorList.getColumnasStr(null));
		map.addAttribute("proveedorList", getDao().getList(0, 100, null));
		map.addAttribute("proveedor", new Proveedor());
		map.addAttribute("tituloFormulario", "Registrar Cliente");
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid Proveedor obj,
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

	@Override
	public Dao<Proveedor> getDao() {
		return proveedorDao;
	}

	// función para actualizar el estado a inactivo en vez de eliminarlo
		private String editarEstado(ModelMap map, Long id_objeto) {
			try {
				Proveedor obj = getDao().find(id_objeto);
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
		public String buscarEditar(ModelMap map, 
				@RequestParam(value = "id_proveedor", required = true) Long idProveedor) {
			try {
				
				Proveedor proveedor = null;
				if (idProveedor != null ) {
					proveedor = getDao().find(idProveedor);
					logger.info("Proveedor encontrado {}", proveedor);
				}
				agregarValoresAdicionales(map);
				map.addAttribute("proveedor", proveedor);
				map.addAttribute("tituloFormulario", "Editar Proveedor");
				map.addAttribute("accion", "editar");

			} catch (Exception ex) {
				Proveedor proveedor = new Proveedor();
				proveedor.setId(null);
				map.addAttribute("error", getErrorFromException(ex));
				map.addAttribute(getNombreObjeto(), proveedor);
				return getTemplatePath();
			}

			
			return "proveedor/proveedor_form";

		}
		
	//metodo eliminar proveedor
	@RequestMapping(value = "eliminar_listado", method = RequestMethod.POST)
	public String eliminar_listado(ModelMap map, @RequestParam("id_objeto") Long id_objeto) {
		Proveedor proveedor = new Proveedor();
		try {
			logger.info("ID DE OBJ {}", id_objeto);
			if (id_objeto != null) {
				proveedor = getDao().find(id_objeto);
				proveedor.setEstado("A");
				proveedorDao.edit(proveedor);
				proveedorDao.destroy(proveedor);
				logger.info("Proveedor eliminado {}", proveedor);
				map.addAttribute("msgExito", msg.get("Registro Eliminado"));
			}
		} catch (Exception ex) {

			proveedor.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), proveedor);
		}

		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		return getTemplatePath();

	}
	
}