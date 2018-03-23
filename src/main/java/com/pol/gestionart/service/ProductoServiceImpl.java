package com.pol.gestionart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.ProductoDao;
import com.pol.gestionart.entity.Producto;


@Service
public class ProductoServiceImpl implements ProductoService {
	@Autowired
	private ProductoDao productoDao;
	
	@Override
	public Iterable<Producto> listAllProducts(){
		return productoDao.findAll();	
	}


	@Override
	public Producto saveProduct(Producto producto) {
		return productoDao.save(producto);
	}
	
	@Override
	public void deleteProduct(String id) {
		productoDao.deleteById(id);
		
	}
	
}
