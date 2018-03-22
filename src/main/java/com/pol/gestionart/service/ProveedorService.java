package com.pol.gestionart.service;

import com.pol.gestionart.entity.Proveedor;

public interface ProveedorService {
	
	Iterable <Proveedor> listAllProv();
	
	Proveedor saveProv(Proveedor proveedor);
	
	void deleteProv(String Id);

}