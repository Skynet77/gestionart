package	 com.pol.gestionart.controller.list;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pol.gestionart.bean.DataTable;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;

@Controller
@Scope("session")
@RequestMapping("inventario")
public class InventarioListController extends ListController<Inventario> {

	@Autowired
	private InventarioDao inventarioDao;

	@Override
	public String[] getColumnas() {
		return new String[] { "id", "fechaMes", "stockInicial", "entrada", "salida", "actual", "producto.descripcion" };
	}

	@Override
	public Dao<Inventario> getDao() {
		return inventarioDao;
	}
	
	
	@RequestMapping(value = "jsonf", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	// required=false es opcional el parametro de la url
	public DataTable<Inventario> json(
			@RequestParam(required = false) String sSearch,
			@RequestParam(required = false, name="sSearch_0") String sSearchFecha,
			@RequestParam(required = false, defaultValue = "0") Integer iDisplayStart,
			@RequestParam(required = false, defaultValue = "10") Integer iDisplayLength) {
			logger.info("FECHA JSON ", sSearchFecha);
			logger.info("json?sSearch={}, iDisplayStart={}, iDisplayLength={}",
				new Object[] { sSearch, iDisplayStart, iDisplayLength });

		try {
			DataTable<Inventario> dt = new DataTable<>();
			Calendar calendar = Calendar.getInstance();
			
			int anho = 0, mes = 0, dia = 0;
			
			
			if(sSearchFecha != null && !"".equals(sSearchFecha)){
				String[] arrayFecha = sSearchFecha.split("-");
				anho= Integer.parseInt(arrayFecha[2]);
				mes =  Integer.parseInt(arrayFecha[1]);
				dia = Integer.parseInt(arrayFecha[0]);
				logger.info("sSearchFecha :", anho,mes,dia);
				calendar.set(anho,mes-1,dia);
			}else{
//				Date d = new Date();
//				anho = d.getYear();
//				mes = d.getMonth();
//				dia = d.getDay();
				calendar = null;
			}
			
			
			List<Inventario> list = inventarioDao.getList(iDisplayStart,  iDisplayStart + iDisplayLength, sSearch, calendar);
			Long size = Long.valueOf(list.size());
			dt.setRecordsTotal(size);
			dt.setAaData(list);
			return dt;
		} catch (Exception ex) {
			logger.error("No se pudo obtener lista", ex);
			return new DataTable<>();
		}
	}
}