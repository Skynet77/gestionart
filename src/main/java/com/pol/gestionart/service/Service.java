package com.pol.gestionart.service;

import java.util.List;

import com.pol.gestionart.entity.Cliente;

public interface Service<T> {
	
	/**
	 * Inserta un registro en la base de datos
	 */
	void create(T obj);

	void createOrUpdate(T obj);

	/**
	 * Actualiza el registro de la base de datos
	 */
	void edit(T obj);

	/**
	 * @return obj con el id indicado
	 */
	T find(Long id);

	/**
	 * Elimina un registro de la base de datos.
	 */
	void destroy(T obj);

	/**
	 * @param sSearch
	 *filtro que se debe aplicar en la búsqueda
	 * @return registros de la tabla
	 */

	List<T> getList(Integer filaInicio, Integer filaFin, String sSearch);

	List<T> getListAll(String sSearch);

	void create(Cliente cliente);


	
}
