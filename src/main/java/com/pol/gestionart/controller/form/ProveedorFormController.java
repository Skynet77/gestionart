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
import com.pol.gestionart.entity.Cliente;
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
			return edit(map, obj.getId());
		} else if (id_objeto != null) {
			return delete(map, id_objeto);

		}
		return getTemplatePath();

	}

	@Override
	public Dao<Proveedor> getDao() {
		return proveedorDao;
	}

	/*
	@RequestMapping(value = "save_listado", method = RequestMethod.POST)
	public String guardar_listado(ModelMap map, 
			@Valid Cliente obj,
			BindingResult bindingResult) {
		try {
			if (obj.getId() == null) {
				Cliente cliente = clienteDao.find(obj.getCliente().getId());
				cliente.setDisponible("NO");
				clienteDao.createOrUpdate(cliente);
				getDao().createOrUpdate(obj);
			}

			map.addAttribute("msgExito", msg.get("Registro agregado"));
			logger.info("Se crea cliente nuevo -> {}", obj);

		} catch (Exception ex) {
			obj.setId(null);
			map.addAttribute("error", getErrorFromException(ex));

		}
		map.addAttribute(getNombreObjeto(), obj);
		agregarValoresAdicionales(map);
		return getTemplatePath();

	}

	@RequestMapping(value = "editar_listado", method = RequestMethod.POST)
	public String editar_listado(ModelMap map, 
			@Valid Cliente obj,
			BindingResult bindingResult) {
		try {
			List<FacturaCabecera> listFactura = new ArrayList<FacturaCabecera>();
			List<Expediente> listExpediente = new ArrayList<Expediente>();
			Persona persona = null;
			if (obj != null ) {
				if (obj.getPersona().getId() != null) {
					persona = personaDao.find(obj.getPersona().getId());
					obj.setPersona(persona);
				}

			}
			
			getDao().createOrUpdate(obj);
			logger.info("Cliente Actualizado {}", obj);
			map.addAttribute("msgExito", msg.get("Registro Actualizado"));

		} catch (Exception ex) {
			obj.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), obj);
		}
		Cliente c = new Cliente();
		map.addAttribute(getNombreObjeto(), c);
		agregarValoresAdicionales(map);
		return getTemplatePath();

	}*/
	
	//metodo eliminar proveedor
	@RequestMapping(value = "eliminar_listado", method = RequestMethod.POST)
	public String eliminar_listado(ModelMap map, @RequestParam("id_objeto") Long id_objeto) {
		Proveedor proveedor = new Proveedor();
		try {
			logger.info("ID DE OBJ {}", id_objeto);
			if (id_objeto != null) {
				proveedor = getDao().find(id_objeto);
				proveedor.setEstado('A');
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