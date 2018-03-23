package com.pol.gestionart.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.entity.Cliente;

@Repository
public class ClienteDaoImpl extends DaoImpl<Cliente> implements ClienteDao{

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return null;
	}

}
