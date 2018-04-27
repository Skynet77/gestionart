package com.pol.gestionart.dao;

import java.util.List;

public interface Dao<T> {
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
	void destroy(String id); //cambie de objeto T a id

	/**
	 * @param sSearch
	 *filtro que se debe aplicar en la búsqueda
	 * @return registros de la tabla
	 */

	List<T> getList(Integer filaInicio, Integer filaFin, String sSearch);

	List<T> getListAll(String sSearch);

	void destroy(T obj);

	List<T> findEntities(boolean all, int maxResults, int firstResult, String name);

}
