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
import com.pol.gestionart.entity.Cliente;
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
		map.addAttribute("tituloFormulario", "Registrar Usuario");
		map.addAttribute("accion", "guardar");
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
		} else if ("delete".equals(accion)) {
			return editarEstado(map, id_objeto);

		}
		return getTemplatePath();

	}

	@Override
	public Dao<Usuario> getDao() {
		return usuarioDao;
	}
	
	// función para actualizar el estado a inactivo en vez de eliminarlo
		private String editarEstado(ModelMap map, Long id_objeto) {
			try {
				Usuario obj = getDao().find(id_objeto);
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
			@RequestParam(value = "id_usuario", required = true) Long idUsuario) {
		try {
			
			Usuario usuario = null;
			if (idUsuario != null ) {
				usuario = usuarioDao.find(idUsuario);
				logger.info("Usuario encontrado {}", usuario);
			}
			agregarValoresAdicionales(map);
			map.addAttribute("usuario", usuario);
			map.addAttribute("tituloFormulario", "Editar Usuario");
			map.addAttribute("accion", "editar");

		} catch (Exception ex) {
			Usuario usuario = new Usuario();
			usuario.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), usuario);
			return getTemplatePath();
		}

		
		return "usuario/usuario_form";

	}
	
	
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