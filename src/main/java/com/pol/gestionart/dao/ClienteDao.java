package com.pol.gestionart.dao;

import com.pol.gestionart.entity.Cliente;


public interface ClienteDao extends Dao<Cliente>{
	Long getCantidadCliente();
}
