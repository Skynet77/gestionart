package com.pol.gestionart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.entity.Cliente;

@Service
public class ClienteServiceImpl extends ServiceImpl<Cliente> {
	
	@SuppressWarnings("unused")
	@Autowired
	private ClienteDao clienteDao;

	@Override
	public Dao<Cliente> getDao() {
		// TODO Auto-generated method stub
		return clienteDao;
	}


}
