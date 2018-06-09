package com.pol.gestionart.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pol.gestionart.dao.Dao;
import com.pol.gestionart.main.GenericEntity;

public abstract class DaoImpl<T extends GenericEntity> implements Dao<T>{
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String UNCHECKED = "unchecked";
	@Autowired 
	protected EntityManager entityManager;
	private String entityName;
	private Class<T> entityClass;
	
	@Transactional
	@Override
	public void create(T obj) {
		
		logger.info("Insertando registro {} ", obj);
		entityManager.persist(obj);
		entityManager.flush();
	}
	
	@Transactional 
	@Override 
	public void createOrUpdate(T obj) {
		
		logger.info("Insertando registro {}",obj);
		entityManager.merge(obj);
		entityManager.flush();
	}
	
	@Transactional
	@Override
	public void edit(T obj) {
		logger.info("Editando registro {}",obj);
		entityManager.merge(obj);
	}
	
	@Transactional
	@Override
	public T find(Long id) {
		logger.info("Buscando un registro con id: {}", id);
		return entityManager.find(getEntityClass(), id);
	}
	
	@Transactional
	@Override
	public void destroy(T obj) {
		logger.info("Borrando Registro {}",obj);
		entityManager.remove(find(obj.getId()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> getList(Integer filaInicio, Integer filaFin, String sSearch){
		logger.info("Obteniendo lista de objetos, sSearch: {}", sSearch);
		
		String sql = "SELECT object(#ENTITY#) FROM #ENTITY# AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", getEntityName());
		Query query = null;
		//el usuario no ha enviado ningun filtro
		//sSearch = "A"; // Solo traerá con estados activos
		if (sSearch== null || "".equals(sSearch)) {
			query = entityManager.createQuery(sql);
		} else if(sSearch.contains(",")){
			String[] param = sSearch.split(",");
			//Ej: A,Coca [A,Coca]
			if(param.length >= 2){
				sql = sql + "WHERE estado = ?1 and lower(" + getCamposFiltrables() + ")LIKE lower (?2)";
				query = entityManager.createQuery(sql);
				query.setParameter(1, param[0]);
				query.setParameter(2, "%" + param[1].replace(" ", "%") + "%");
			}else{
				sql = sql + "WHERE estado = ?1 and lower(" + getCamposFiltrables() + ")LIKE lower (?2)";
				query = entityManager.createQuery(sql);
				query.setParameter(1, param[0]);
				query.setParameter(2, "%" + param[0].replace(" ", "%") + "%");
			}
			
		} else {
			sql = sql + "WHERE lower(" + getCamposFiltrables() + ")LIKE lower (?1)";
			query = entityManager.createQuery(sql);
			query.setParameter(1, "%" + sSearch.replace(" ", "%") + "%");
		}
		query.setFirstResult(filaInicio);
		query.setMaxResults(filaFin);
		List<T> list = query.getResultList();
		logger.info("Cantidad de registros encontrados: {}",list);
		return list;		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<T> getListAll(String sSearch){
		logger.info("Obteniendo lista de {}, sSearch: {}", getEntityName(),sSearch);
		
		String sql = "SELECT object (#ENTITY#) FROM #ENTITY AS #ENTITY# ";
		sql = sql.replace("#ENTITY#", getEntityName());
		Query query = null;
		
		if (" ".equals(sSearch)) {
			query = entityManager.createQuery(sql);
		} else {
			sql = sql + "WHERE lower(" + getCamposFiltrables() + ") LIKE lower(?1)";
			query = entityManager.createQuery(sql);
			query.setParameter(1, "%" + sSearch.replace(" ", "%") + "%");
		}
		List<T> list = query.getResultList();
		logger.info("Cantidad de registros encontrados): {}", list);
		return list;
	}
	
	public abstract String getCamposFiltrables();
	
	public String getEntityName() {
		if (entityName == null) {
			Entity entity = getEntityClass().getAnnotation(Entity.class);
			if (entity.name() == null || entity.name().compareTo("") == 0) {
				entityName = getEntityClass().getSimpleName();
			}else 
				entityName = entity.name();
		}
		return entityName;
	}
	
	@SuppressWarnings(UNCHECKED)
	public Class<T> getEntityClass(){
		if (entityClass == null) {
			ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
			logger.info(String.valueOf(this.getClass()));
			entityClass = (Class<T>) superClass.getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	protected void initializeCollection(Collection<?> collection) {
		if (collection == null) {
			return;
		}
		collection.iterator().hasNext();
	}
	
	@Transactional
	@SuppressWarnings(UNCHECKED)
	public List<T> findEntities(boolean all, int maxResults, int firstResult) {

		EntityManager em = entityManager;
		String sql = "select object(ENTITY) from ENTITY as ENTITY";
		sql = sql.replace("ENTITY", getEntityName());
		logger.debug("Realizando consulta: {}", sql);

		Query query = em.createQuery(sql);
		if (!all) {
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
		}
		List<T> list = query.getResultList();
		logger.info("Registros encontrados: {}", list.size());
		return list;
	}
	
	@Transactional
	@Override
	@SuppressWarnings(UNCHECKED)
	public List<T> findEntities(boolean all, int maxResults, int firstResult, String name) {

		if(name== null){
			EntityManager em = entityManager;
			String sql = "select object(ENTITY) from ENTITY as ENTITY";
			sql = sql.replace("ENTITY", getEntityName());
			logger.debug("Realizando consulta: {}", sql);

			Query query = em.createQuery(sql);
			if (!all) {
				query.setMaxResults(maxResults);
				query.setFirstResult(firstResult);
			}
			List<T> list = query.getResultList();
			logger.info("Registros encontrados: {}", list.size());
			return list;
		}else{
			EntityManager em = entityManager;
			String sql = "select object(ENTITY) from ENTITY as ENTITY";
			sql = sql.replace("ENTITY", name);
			logger.debug("Realizando consulta: {}", sql);

			Query query = em.createQuery(sql);
			if (!all) {
				query.setMaxResults(maxResults);
				query.setFirstResult(firstResult);
			}
			List<T> list = query.getResultList();
			logger.info("Registros encontrados: {}", list.size());
			return list;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}