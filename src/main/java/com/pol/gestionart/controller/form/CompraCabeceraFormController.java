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

import com.pol.gestionart.controller.list.CompraCabeceraListcontroller;
import com.pol.gestionart.dao.CompraCabeceraDao;
import com.pol.gestionart.dao.CompraDetalleDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.entity.Cliente;
import com.pol.gestionart.entity.CompraCabecera;


@Controller
@Scope("request")
@RequestMapping("compra_cabecera")
public class CompraCabeceraFormController extends FormController<CompraCabecera> {
	
	/**@Autowired
	private ClienteFacturaDao clienteFacturaDao;*/ //falta revisar
	
	@Autowired 
	private CompraCabeceraDao compraCabeceraDao;
	
	@Autowired
	private CompraCabeceraListcontroller compraCabeceraList;
	
	@Autowired 
	private ProveedorDao proveedorDao;
	
	@Autowired
	private CompraDetalleDao compraDetalleDao;

	@Override
	public String getTemplatePath() {
		return "compra/compra_cabecera_index";
	}

	@Override
	public String getNombreObjeto() {
		return "compraCabecera";
	}

	@Override
	public CompraCabecera getNuevaInstancia() {
		return new CompraCabecera();
	}
	
	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", compraCabeceraList.getColumnas());
		map.addAttribute("columnasStr", compraCabeceraList.getColumnasStr(null));
		map.addAttribute("proveedorList", getDao().getList(0, 100, null));
		map.addAttribute("productoList", getDao().getList(0, 100, null));
		map.addAttribute("compraCabecera", new CompraCabecera());
		map.addAttribute("tituloFormulario", "Registrar Compra");
		map.addAttribute("accion", "guardar");
		super.agregarValoresAdicionales(map);
	}
	
	@RequestMapping(value = "accion2", method = RequestMethod.POST)
	public String accion2(ModelMap map, @Valid CompraCabecera obj,
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
				CompraCabecera obj = getDao().find(id_objeto);
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
		@RequestParam(value = "id_compra", required = true) Long idCompra) {
			try {
				CompraCabecera compraCabecera = null;
				if (idCompra != null ) {
					compraCabecera = compraCabeceraDao.find(idCompra);
					logger.info("Compra encontrada {}", compraCabecera);
				}
				agregarValoresAdicionales(map);
				map.addAttribute("compraCabecera", compraCabecera);
				map.addAttribute("tituloFormulario", "Editar Compra");
				map.addAttribute("accion", "editar");

			} catch (Exception ex) {
				CompraCabecera compraCabecera = new CompraCabecera();
				compraCabecera.setId(null);
				map.addAttribute("error", getErrorFromException(ex));
				map.addAttribute(getNombreObjeto(), compraCabecera);
				return getTemplatePath();
			}
	
		return "cliente/compraCabecera_form";

	}
	
	//metodo eliminar 
	@RequestMapping(value = "eliminar_listado", method = RequestMethod.POST)
	public String eliminar_listado(ModelMap map, @RequestParam("id_objeto") Long id_objeto) {
		CompraCabecera compraCabecera = new CompraCabecera();
		try {
			logger.info("ID DE OBJ {}", id_objeto);
			if (id_objeto != null) {
				compraCabecera = getDao().find(id_objeto);
				compraCabeceraDao.edit(compraCabecera);
				compraCabeceraDao.destroy(compraCabecera);
				logger.info("Compra eliminada {}", compraCabecera);
				map.addAttribute("msgExito", msg.get("Registro Eliminado"));
			}
		} catch (Exception ex) {

			compraCabecera.setId(null);
			map.addAttribute("error", getErrorFromException(ex));
			map.addAttribute(getNombreObjeto(), compraCabecera);
		}

		map.addAttribute(getNombreObjeto(), getNuevaInstancia());
		agregarValoresAdicionales(map);
		return getTemplatePath();

		}

	@Override
	public Dao<CompraCabecera> getDao() {
		return compraCabeceraDao;
	}

}
