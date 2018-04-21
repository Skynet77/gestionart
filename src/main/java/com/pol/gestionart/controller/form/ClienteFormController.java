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

import com.pol.gestionart.controller.list.ClienteListController;
import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Cliente;



@Controller
@Scope("request")
@RequestMapping("cliente")
public class ClienteFormController extends FormController<Cliente> {

	@Autowired
	private ClienteDao clienteDao;
	

	@Autowired
	private ClienteListController clienteList;

	
	@Override
	public String getTemplatePath() {
		return "cliente/cliente_index";
	}

	@Override
	public String getNombreObjeto() {
		return "cliente";
	}

	@Override
	public Cliente getNuevaInstancia() {
		return new Cliente();
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", clienteList.getColumnas());
		map.addAttribute("columnasStr", clienteList.getColumnasStr(null));
		map.addAttribute("clienteList", getDao().getList(0, 100, null));
		map.addAttribute("cliente", new Cliente());
		map.addAttribute("tituloFormulario", "Registrar Cliente");
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid Cliente obj,
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
			Cliente obj = getDao().find(id_objeto);
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

	@Override
	public Dao<Cliente> getDao() {
		return clienteDao;
	}

	
	@RequestMapping(value = "buscar", method = RequestMethod.POST)
	public String buscarEditar(ModelMap map, 
			@RequestParam(value = "id_cliente", required = true) Long idCliente) {
		try {
			
			Cliente cliente = null;
			if (idCliente != null ) {
				cliente = clienteDao.find(idCliente);
				logger.info("Cliente encontrado {}", cliente);
			}
			agregarValoresAdicionales(map);
			map.addAttribute("cliente", cliente);
			map.addAttribute("tituloFormulario", "Editar Cliente");
			map.addAttribute("accion", "editar");

		} catch (Exception ex) {
			Cliente cliente = new Cliente();
			cliente.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), cliente);
			return getTemplatePath();
		}

		
		return "cliente/cliente_form";

	}
	


	//metodo eliminar cliente
	@RequestMapping(value = "eliminar_listado", method = RequestMethod.POST)
	public String eliminar_listado(ModelMap map, @RequestParam("id_objeto") Long id_objeto) {
		Cliente cliente = new Cliente();
		try {
			logger.info("ID DE OBJ {}", id_objeto);
			if (id_objeto != null) {
				cliente = getDao().find(id_objeto);
				cliente.setEstado("A");
				clienteDao.edit(cliente);
				clienteDao.destroy(cliente);
				logger.info("Cliente eliminado {}", cliente);
				map.addAttribute("msgExito", msg.get("Registro Eliminado"));
			}
		} catch (Exception ex) {

			cliente.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), cliente);
		}

		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		return getTemplatePath();

	}

}