package com.pol.gestionart.dao;

import com.pol.gestionart.entity.User;
import com.pol.gestionart.entity.Usuario;

public interface UserDao extends Dao<Usuario>{

	public User findByUsername(String username);
	
}
