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

import com.pol.gestionart.controller.list.UsuarioListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;

@Controller
@Scope("request")
@RequestMapping("usuario")
public class UsuarioFormController extends FormController<Usuario> {

	@Autowired
	private UsuarioDao usuarioDao;
	

	@Autowired
	private UsuarioListController usuarioList;

	
	@Override
	public String getTemplatePath() {
		return "usuario/usuario_index";
	}

	@Override
	public String getNombreObjeto() {
		return "usuario";
	}

	@Override
	public Usuario getNuevaInstancia() {
		return new Usuario();
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", usuarioList.getColumnas());
		map.addAttribute("columnasStr", usuarioList.getColumnasStr(null));
		map.addAttribute("usuarioList", getDao().getList(0, 100, null));
		map.addAttribute("usuario", new Usuario());
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid Usuario obj,
			BindingResult bindingResult,
			@RequestParam(required = false) String accion,
			@RequestParam(value = "id_objeto", required = false) Long id_objeto) {
		if (StringUtils.equals(accion, "save")) {
			return guardar(map, obj, bindingResult);
		} else if (StringUtils.equals(accion, "edit")) {
			logger.info("OBJETO PROCESO {}", obj);
			return edit(map, obj.getId(),obj);
		} else if (id_objeto != null) {
			return delete(map, id_objeto);

		}
		return getTemplatePath();

	}

	@Override
	public Dao<Usuario> getDao() {
		return usuarioDao;
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
	
	//metodo eliminar usuario
	@RequestMapping(value = "eliminar_listado", method = RequestMethod.POST)
	public String eliminar_listado(ModelMap map, @RequestParam("id_objeto") Long id_objeto) {
		Usuario usuario = new Usuario();
		try {
			logger.info("ID DE OBJ {}", id_objeto);
			if (id_objeto != null) {
				usuario = getDao().find(id_objeto);
				//usuario.setEstado('A');
				usuarioDao.edit(usuario);
				usuarioDao.destroy(usuario);
				logger.info("Usuario eliminado {}", usuario);
				map.addAttribute("msgExito", msg.get("Registro Eliminado"));
			}
		} catch (Exception ex) {

			usuario.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), usuario);
		}

		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		return getTemplatePath();

	}
	
}