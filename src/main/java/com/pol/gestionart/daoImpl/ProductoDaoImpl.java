package com.pol.gestionart.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.bean.Donut;
import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Producto;
@Repository
public class ProductoDaoImpl extends DaoImpl<Producto> implements ProductoDao {

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return "idproducto||estado||descripcion||codigo";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Donut> getDonutReport() {
		logger.info("Obteniendo la cantidad de venta cabecera");
		Long total = (long) 0;
		Query query = null;
		List<Donut> listDonut = new ArrayList<Donut>();;
		try {
			String sql = "SELECT f.nombre, coalesce(sum(vd.cantidad),0) FROM Familia AS f"+ 
			" left join Producto AS p on f.id = p.familia.id"+ 
			" left join VentaDetalle vd on vd.producto.id = p.id"+
			" group by f.nombre";
			query = entityManager.createQuery(sql);
			List<Object[]> t = query.getResultList();
			String d = null;
			for (Object[] ob : t) {
				Donut donut = new Donut((String)ob[0],((Long)ob[1]).intValue());
				listDonut.add(donut);
			}
			
		} catch (Exception e) {
			total = (long) 0;
		}
		return listDonut;
	}

}
