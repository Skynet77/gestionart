package com.pol.gestionart.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pol.gestionart.entity.Proveedor;


@Repository
public interface ProveedorDao extends JpaRepository <Proveedor, Serializable> {

}