package com.pol.gestionart.dao;

import java.util.List;

import com.pol.gestionart.entity.Caja;


public interface CajaDao extends Dao<Caja>{
	public Caja findCajaByDate();

	List<Caja> findCajaForCierre();
}
