package com.pol.gestionart.dao;

import com.pol.gestionart.entity.Caja;


public interface CajaDao extends Dao<Caja>{
	public Caja findCajaByDate();
}
