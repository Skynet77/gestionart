package com.pol.gestionart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.ProveedorDao;
import com.pol.gestionart.entity.Proveedor;


@Service
public class ProveedorServiceImpl implements ProveedorService {
	@Autowired
	private ProveedorDao proveedorDao;
	
	@Override
	public Iterable<Proveedor> listAllProv(){
		return proveedorDao.findAll();	
	}


	@Override
	public Proveedor saveProv(Proveedor proveedor) {
		return proveedorDao.save(proveedor);
	}
	
	@Override
	public void deleteProv(String id) {
		proveedorDao.deleteById(id);
		
	}
	
}