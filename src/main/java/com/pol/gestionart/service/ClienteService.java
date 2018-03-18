package com.pol.gestionart.service;

import java.util.List;

import com.pol.gestionart.entity.Cliente;

public interface ClienteService {
	public abstract List<Cliente> listarClientes();
	
	public boolean agregarCliente(Cliente cliente);

}
