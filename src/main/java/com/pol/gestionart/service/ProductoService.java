package com.pol.gestionart.service;

import com.pol.gestionart.entity.Producto;

public interface ProductoService {
	
	Iterable <Producto> listAllProducts();
	
	Producto saveProduct(Producto producto);
	
	void deleteProduct(String Id);

}
