package com.pol.gestionart.daoImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.InventarioDao;
import com.pol.gestionart.entity.Inventario;
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
	public Inventario getInventarioByProductoFecha(Long idProducto){
		Inventario inve = null;
		try {
		
			String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
			sql = sql.replace("#ENTITY#", getEntityName());
			Query query = null;
			Calendar calendar = Calendar.getInstance();
			//calendar.set(Calendar.DATE, 1);
			Date d = new Date(calendar.getTimeInMillis());
			int mes = d.getMonth();
			mes = mes+1;
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

}
