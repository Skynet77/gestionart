package com.pol.gestionart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pol.gestionart.dao.ClienteDao;
import com.pol.gestionart.dao.Dao;

public abstract class ServiceImpl<T> implements Service{
	
	
	@Override
	public void create(Object obj) {
		//clienteDao.create(obj);
		getDao().create((T) obj);
		
	}
	
	public void createOrUpdate(Object obj){
		getDao().createOrUpdate((T) obj);
	}
	
	@Override
	public void edit(Object obj) {
		//clienteDao.create(obj);
		getDao().edit((T) obj);
	}
	
	public T find(Long id){
		return getDao().find(id);
	}
	
	public void destroy(Object obj){
		getDao().destroy((T) obj);
	}
	
	public List<T> getList(Integer filaInicio, Integer filaFin, String sSearch){
		return getDao().getList(filaInicio, filaFin, sSearch);
	}

	public List<T> getListAll(String sSearch){
		return getDao().getListAll(sSearch);
	}

	public abstract Dao<T> getDao();
}
