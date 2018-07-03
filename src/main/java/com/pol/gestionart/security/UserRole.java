package com.pol.gestionart.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotEmpty;

import com.pol.gestionart.main.GenericEntity;

@Entity
@Table(name="user_role", uniqueConstraints=@UniqueConstraint(columnNames= {"rol","username"}))
public class UserRole{
	private static final String SECUENCIA = "rol_user_id_seq";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SECUENCIA)
	@SequenceGenerator(name = SECUENCIA, sequenceName = SECUENCIA)
	private Long id;

	private Integer userRoleId;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name ="username", nullable=false)
	private Usuario user;
	
	@Column(name="rol", nullable = false, length=45)
	private Rol rol;
	
	public UserRole() {
		super();
	}
	public UserRole(Long id, Integer userRoleId, Usuario user, Rol rol) {
		super();
		this.id = id;
		this.userRoleId = userRoleId;
		this.user = user;
		this.rol = rol;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
}
