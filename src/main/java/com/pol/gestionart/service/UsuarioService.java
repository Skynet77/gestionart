package com.pol.gestionart.service;

import com.pol.gestionart.entity.Usuario;

public interface UsuarioService {
	
	Iterable <Usuario> listAllUsers();
	
	Usuario addUser(Usuario usuario);
	
	Usuario updateUser(Usuario usuario);
	
	void deleteUser(String Id);

}