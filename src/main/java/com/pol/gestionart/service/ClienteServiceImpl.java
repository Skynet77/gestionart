package com.pol.gestionart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@SuppressWarnings("unused")
	@Autowired
	private ClienteDao clienteDao;
	

	@Override
	public List<Cliente> listarClientes() {
//		return clienteDao.findAll();
		return null;
	}


	@Override
	public boolean agregarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}

}
