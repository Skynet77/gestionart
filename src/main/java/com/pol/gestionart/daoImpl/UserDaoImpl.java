package com.pol.gestionart.daoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.security.core.userdetails.User;

import com.pol.gestionart.dao.UserDao;
import com.pol.gestionart.dao.UsuarioDao;
import com.pol.gestionart.entity.UserRole;
import com.pol.gestionart.entity.Usuario;

@Repository
public class UserDaoImpl extends DaoImpl<Usuario> implements UserDao, UserDetailsService{

	@Autowired 
	protected EntityManager entityManager;
	
	
	@Override
	public String getCamposFiltrables() {
		return "estado||cedulaRuc||nombreRazonSocial||apellido";
	}

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.pol.gestionart.entity.User user = findByUsername(username);
		List<GrantedAuthority> authorities = buildAuthorities(user.getUserRole());
		return buildUser(user, authorities);
	}
	
	private User buildUser(com.pol.gestionart.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(Set<UserRole> userRoles){
		Set<GrantedAuthority> auths = new HashSet<GrantedAuthority>();
		for (UserRole userRole : userRoles) {
			auths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
		return new ArrayList<GrantedAuthority>(auths);
	}

	@Override
	public com.pol.gestionart.entity.User findByUsername(String username) {
		String sql = "SELECT object(User) FROM User AS User ";
		sql = sql + "WHERE username = ?1";
		Query query = null;
		query = entityManager.createQuery(sql);
		query.setParameter(1, username);
		com.pol.gestionart.entity.User u = (com.pol.gestionart.entity.User) query.getSingleResult();
		return u;
	}



}