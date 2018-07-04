package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.bean.Donut;
import com.pol.gestionart.entity.Producto;

public interface ProductoDao extends Dao<Producto>{
	List<Donut> getDonutReport();
}
