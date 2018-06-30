package com.pol.gestionart.dao;

import com.pol.gestionart.security.Usuario;

public interface UsuarioDao extends Dao<Usuario>{

	Usuario buscar(String codigo);
}
