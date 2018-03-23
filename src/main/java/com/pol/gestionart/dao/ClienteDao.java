package com.pol.gestionart.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pol.gestionart.entity.Cliente;


//public interface ClienteDao extends JpaRepository <Cliente,Serializable>{
public interface ClienteDao extends Dao<Cliente>{

}
