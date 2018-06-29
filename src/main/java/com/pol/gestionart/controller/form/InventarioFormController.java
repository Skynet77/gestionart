package com.pol.gestionart.controller.form;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pol.gestionart.bean.InventarioDetalle;
import com.pol.gestionart.controller.list.InventarioListController;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;

@Controller
@Scope("request")
@RequestMapping("inventario")
public class InventarioFormController extends FormController<Inventario> {

	@Autowired
	private InventarioDao inventarioDao;

	@Autowired
	private InventarioListController inventarioList;

	@Override
	public String getTemplatePath() {
		return "inventario/inventario_index";
	}

	@Override
	public String getNombreObjeto() {
		return "inventario";
	}

	@Override
	public Inventario getNuevaInstancia() {
		return new Inventario();
	}

	@Override
	public Dao<Inventario> getDao() {
		return inventarioDao;
	}

	@Override
	public void agregarValoresAdicionales(ModelMap map) {
		map.addAttribute("columnas", inventarioList.getColumnas());
		map.addAttribute("columnasStr", inventarioList.getColumnasStr(null));
		super.agregarValoresAdicionales(map);
	}

	@RequestMapping(value = "inventario_detalle", method = RequestMethod.POST)
	public String inventarioDetalle(ModelMap map, HttpSession session, @RequestParam(value = "id_prod") Long idProd,
			@RequestParam(value = "mes") int mesFe) {
		agregarValoresAdicionales(map);
		List<InventarioDetalle> inventarioDetalle = inventarioDao.joinInventario(idProd, mesFe);
		map.addAttribute("listDetalle", inventarioDetalle);
		return "inventario/modal_inventario";
	}

	@RequestMapping(value = "ajuste_inventario", method = RequestMethod.GET)
	public String ajusteInventario(ModelMap map, HttpSession session) {
		agregarValoresAdicionales(map);

		return "inventario/ajuste_inventario";
	}

	@RequestMapping(value = "ajuste_inventario", method = RequestMethod.POST)
	public String ajusteInventario(ModelMap map, HttpSession session, @RequestParam(value = "id_prod") Long idProd,
			@RequestParam(value = "fecha_mes") String fechaMes, @RequestParam(value = "cantidad") int cantidad) {

		// cargar en inventarioDetalleTable

		return "inventario/ajuste_inventario";
	}

}