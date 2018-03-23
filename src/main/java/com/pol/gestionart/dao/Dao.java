package com.pol.gestionart.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

@ComponentScan
public interface Dao<T>{
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
	 *
	 *            filtro que se debe aplicar en la búsqueda
	 * @return registros de la tabla
	 */

	List<T> obtenerListadoPorFiltro(Integer filaInicio, Integer filaFin, String sSearch);

	
	List<T> getListAll(String sSearch);
	

}
