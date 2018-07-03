package com.pol.gestionart.daoImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.bean.InventarioDetalle;
import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;
import com.pol.gestionart.entity.InventarioDetalleTable;
@Repository
public class InventarioDaoImpl extends DaoImpl<Inventario> implements InventarioDao {

	@Override
	public String getCamposFiltrables() {
		return "producto.codigo||producto.descripcion";
	}

	@Override
	public void destroy(String id) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Inventario> getList(Integer filaInicio, Integer filaFin, String sSearch, Calendar sSearch2){
		logger.info("Obteniendo lista de objetos, sSearch: {}", sSearch);
		
		String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", getEntityName());
		Query query = null;
		//el usuario no ha enviado ningun filtro
		//sSearch = "A"; // Solo traerá con estados activos
		if ((sSearch== null || "".equals(sSearch)) && (sSearch2 == null)|| "".equals(sSearch2)) {
			sql = sql + " order by fechames desc";
			query = entityManager.createQuery(sql);
		} else if(sSearch.contains(",")){
			String[] param = sSearch.split(",");
			//Ej: A,Coca [A,Coca]
			if(param.length >= 2){
				sql = sql + "WHERE estado = ?1 and lower(" + getCamposFiltrables() + ")LIKE lower (?2)";
				query = entityManager.createQuery(sql);
				query.setParameter(1, param[0]);
				query.setParameter(2, "%" + param[1].replace(" ", "%") + "%");
			}else{
				sql = sql + "WHERE estado = ?1 and lower(" + getCamposFiltrables() + ")LIKE lower (?2)";
				query = entityManager.createQuery(sql);
				query.setParameter(1, param[0]);
				query.setParameter(2, "%" + param[0].replace(" ", "%") + "%");
			}
			
		} else if(sSearch2 !=null) {
			sql = sql + "WHERE lower(" + getCamposFiltrables() + ")LIKE lower (?1) and fechames = ?2";
			query = entityManager.createQuery(sql);
			query.setParameter(1, "%" + sSearch.replace(" ", "%") + "%");
			query.setParameter(2, sSearch2, TemporalType.DATE);
			
		} else {
			sql = sql + "WHERE lower(" + getCamposFiltrables() + ")LIKE lower (?1) order by fechames desc";
			query = entityManager.createQuery(sql);
			query.setParameter(1, "%" + sSearch.replace(" ", "%") + "%");
		}
		query.setFirstResult(filaInicio);
		query.setMaxResults(filaFin);
		List<Inventario> list = query.getResultList();
		logger.info("Cantidad de registros encontrados: {}",list);
		return list;		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Inventario getInventarioByProductoFecha(Long idProducto,Integer mess){
		Inventario inve = null;
		try {
		
			String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
			sql = sql.replace("#ENTITY#", getEntityName());
			Query query = null;
			Calendar calendar = Calendar.getInstance();
			//calendar.set(Calendar.DATE, 1);
			Date d = new Date(calendar.getTimeInMillis());
			int mes = d.getMonth();
			if(mess==null){
				mes = mes+1;
			}else{
				mes = mess;
			}
			
			sql = sql + "WHERE producto_idproducto = ?1 and extract(MONTH from fechames) = ?2";
			query = entityManager.createQuery(sql);
			query.setParameter(1, idProducto);
			query.setParameter(2, mes);
		
			inve = (Inventario) query.getSingleResult();
			logger.info(" registros encontrados: {}",inve);
		} catch (Exception e) {
			inve =null;
		}
		return inve;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InventarioDetalle> joinInventario(Long idProducto,Integer mess){
		InventarioDetalle inve = null;
		List<InventarioDetalle> inventarioList = new ArrayList<InventarioDetalle>();
		try {
		
			
			Query results = entityManager.createQuery(
					"select vc.fechaEmision, vd.cantidad, vc.nroComprobante, c.nombre || ' ' ||c.apellido FROM VentaDetalle as vd "+ 
	"join VentaCabecera as vc on vd.ventaCabecera.idVenta = vc.idVenta "+
	"join Cliente as c on c.id = vc.cliente.id "+
	"join Inventario as i on i.producto.id = vd.producto.id where extract(MONTH from i.fechaMes) = ?1 and i.producto.id = ?2"+
	" order by i.fechaMes asc");
			results.setParameter(1, mess);
			results.setParameter(2, idProducto);
			
					//.getResultList();
			List<Object[]> t = results.getResultList();
			for (Object[] objects : t) {
				InventarioDetalle iv = new InventarioDetalle();
				iv.setFecha((String) objects[0]);
				iv.setCantidad((Integer) objects[1]);
				iv.setComprobante((String) objects[2]);
				iv.setProveedorCliente((String) objects[3]);
				iv.setOperacion("VENTA");
				inventarioList.add(iv);
			}
			
			Query resultsCompra = entityManager.createQuery(
					"select vc.fechaCompra, vd.cantidad, vc.nroComprobante, c.nombre FROM CompraDetalle as vd"+
					" join CompraCabecera as vc on vd.compraCabecera.id = vc.id"+
					" join Proveedor as c on c.id = vc.proveedor.id"+
					" join Inventario as i on i.producto.id = vd.producto.id where extract(MONTH from i.fechaMes) = ?1 and i.producto.id= ?2"+
					" order by i.fechaMes asc");
			resultsCompra.setParameter(1, mess);
			resultsCompra.setParameter(2, idProducto);
			
			t = resultsCompra.getResultList();
			
			for (Object[] objects : t) {
				InventarioDetalle iv = new InventarioDetalle();
				iv.setFecha((String) objects[0]);
				iv.setCantidad((Integer) objects[1]);
				iv.setComprobante((String) objects[2]);
				iv.setProveedorCliente((String) objects[3]);
				iv.setOperacion("COMPRA");
				inventarioList.add(iv);
			}
			
			Query resultsDetalle = entityManager.createQuery("SELECT object(InventarioDetalleTable) FROM InventarioDetalleTable AS InventarioDetalleTable"+
			" where idProducto = ?1 and mes = ?2 order by fechaMes asc");
			resultsDetalle.setParameter(1, idProducto);
			resultsDetalle.setParameter(2, mess);
			
			List<InventarioDetalleTable> t1 = resultsDetalle.getResultList();
			
			for (InventarioDetalleTable objects : t1) {
				InventarioDetalle iv = new InventarioDetalle();
				iv.setCantidad(objects.getCantidad());
				iv.setComprobante(objects.getComprobante());
				iv.setFecha(objects.getFecha());
				iv.setOperacion(objects.getOperacion());
				iv.setProveedorCliente(objects.getProveedorCliente());
				inventarioList.add(iv);
			}
			
			logger.info(" registros encontrados: {}",t);
		} catch (Exception e) {
			inve =null;
		}
		Collections.sort(inventarioList);
		return inventarioList;
	}

}
