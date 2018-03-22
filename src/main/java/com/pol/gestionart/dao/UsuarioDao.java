package com.pol.gestionart.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pol.gestionart.entity.Usuario;


@Repository
public interface UsuarioDao extends JpaRepository <Usuario, Serializable> {

}