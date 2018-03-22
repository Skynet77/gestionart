package com.pol.gestionart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	public Iterable<Usuario> listAllUsers(){
		return usuarioDao.findAll();	
	}


	@Override
	public Usuario saveUser(Usuario usuario) {
		return usuarioDao.save(usuario);
	}
	
	@Override
	public void deleteUser(String id) {
		usuarioDao.deleteById(id);
		
	}
	
}