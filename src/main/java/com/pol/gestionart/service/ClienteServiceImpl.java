package com.pol.gestionart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteDao clienteDao;
	

	@Override
	public List<Cliente> listarClientes() {
		return clienteDao.findAll();
	}


	@Override
	public boolean agregarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean modificarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void eliminarCliente(String id) {
		clienteDao.deleteById(id);
		
	}


}
