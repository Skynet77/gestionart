package com.pol.gestionart.controller.form;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.main.GenericEntity;
import com.pol.gestionart.main.Message;
import com.pol.gestionart.main.SesionUsuario;
import com.pol.gestionart.bean.Respuesta;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.security.Usuario;



public abstract class FormController<T extends GenericEntity>{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired 
	protected Message msg;
	
	@Autowired
	private SesionUsuario session;
	
	@Autowired
	private SesionUsuario sesionUsuario;
	
	@RequestMapping("")
	public String index(ModelMap map) {
		
		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		return getTemplatePath(); 
		}
	
	@RequestMapping(value = "start")
	public String indexStart(ModelMap map) {
		return "starter";
	}
	
	@RequestMapping(value = "accion", method = RequestMethod.POST)
	public String accion(ModelMap map, @Valid T obj, BindingResult bindingResult,
		@RequestParam(required = false) String accion) {
		
		if("edit".equalsIgnoreCase(accion)){
			return edit(map,obj.getId(), obj);
		}else if (accion!= null && !"".equals(accion)) {
			return delete (map, obj.getId());
		} else {
			return guardar(map, obj, bindingResult);
		}	
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String guardar(ModelMap map, @Valid T obj, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				map.addAttribute("error", msg.get("errores_validacion"));
				List<FieldError> errores = bindingResult.getFieldErrors();
				map.addAttribute("errorList", errores);
			} else {
				if (obj.getId() == null) {
					getDao().create(obj);
					map.addAttribute("msgExito", msg.get("registro agregado"));
				} else {
					getDao().edit(obj);
					map.addAttribute("msgExito", msg.get("Registro actualizado"));
				}
				
			}
		} catch (Exception ex) {
			obj.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
		}
		map.addAttribute(getNombreObjeto(), obj);
		agregarValoresAdicionales(map);
		return getTemplatePath();
	}
	
	@RequestMapping("edit/{id}")
	public String edit (ModelMap map, @PathVariable Long id, T objeto) {
		try {
			T obj = getDao().find(id);
			if (obj == null) {
				map.addAttribute("error", "No se encontraron registros con el id "+ id);
				obj = getNuevaInstancia();
			}else{
				getDao().createOrUpdate(objeto);
			}
			map.addAttribute(getNombreObjeto(),obj);
		} catch (Exception ex) {
			map.addAttribute("error", "Error al buscar el registro " + ex.getMessage());
			map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		} 
		
		agregarValoresAdicionales(map);
		return getTemplatePath();
	}
	
	@RequestMapping("delete/{id}")
	public String delete(ModelMap map, @PathVariable Long id) {
		try {
			T obj = getDao().find(id);
			if (obj == null) {
				map.addAttribute("error", "No se han encontrado registros con el id: " + id);
			} else {
				getDao().destroy(obj);
				obj = getNuevaInstancia();
				map.addAttribute(getNombreObjeto(), obj);
				logger.info("registro eliminado");
			}
		} catch (Exception ex){
			map.addAttribute("error", "Error al eliminar el registro. " + ex.getMessage());
		}
		agregarValoresAdicionales(map);
		logger.info("Registro retorna {}", getTemplatePath());
		return getTemplatePath();
	}
	
	public abstract String getTemplatePath();
	
	public abstract String getNombreObjeto();
	
	public abstract T getNuevaInstancia();
	
	
	protected String getErrorFromException(final Exception exception) {
		Throwable th = exception;
		while (th != null) {
			if (th.getClass().equals(org.hibernate.exception.ConstraintViolationException.class)) {
				ConstraintViolationException cve = (ConstraintViolationException) th;
				return msg.get(cve.getConstraintName());
			}
			th = th.getCause();
		}
		return exception.getMessage();
	}

	public void agregarValoresAdicionales(ModelMap map) {

	}

	@ModelAttribute("currentUserName")
	public String currentUserName() {
		if (sesionUsuario.isLogger()) {
			Usuario u = sesionUsuario.getUsuario();
			return u.getNombreRazonSocial() + "(" + u.getCedulaRuc() + ")";
		}
		return "Anónimo";
	}
	/* Desde aca es Persist */

	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public Respuesta<T> find(@PathVariable Long id) {
		Respuesta<T> resp = new Respuesta<>();

		try {
			T obj = getDao().find(id);
			if (obj == null) {
				throw new Exception("No se encontró usuario");
			}
			resp.setDato(obj);
			resp.setExito(true);
		} catch (Exception ex) {
			resp.setExito(false);
			resp.setMensajeError(ex.getMessage());

		}
		return resp;
	}

	public abstract Dao<T> getDao();
}


