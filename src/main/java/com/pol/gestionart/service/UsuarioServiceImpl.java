package com.pol.gestionart.service;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.Usuario;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioDao usuarioDao;
	
	private static final Log LOG = LogFactory.getLog(UsuarioServiceImpl.class);
	
	@Override
	public Iterable<Usuario> listAllUsers(){
		LOG.info("Call: "+ "listAllUsers()");
		return usuarioDao.findAll();	
	}

	@Override
	public Usuario addUser(Usuario usuario) {
		LOG.info("Call: "+ "addUser()");
		return usuarioDao.save(usuario);
	}
	
	@Override
	public void deleteUser(String id) {
		usuarioDao.deleteById(id);
		
	}

	@Override
	public Usuario updateUser(Usuario usuario) {
		return usuarioDao.save(usuario);
	}
	
}