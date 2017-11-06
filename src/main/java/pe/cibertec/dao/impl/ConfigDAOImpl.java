package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pe.cibertec.dao.IConfigDAO;
import pe.cibertec.models.Config;

@Stateless
public class ConfigDAOImpl implements IConfigDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	EntityManager manager;

	@Override
	public void registrar(Config config) throws Exception {
		manager.persist(config);
	}

	@Override
	public void modificar(Config config) throws Exception {
		manager.merge(config);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> listar() throws Exception {
		List<Config> listado;
		listado = (List<Config>) manager.createQuery("FROM Config c").getResultList();
		return listado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Config listarPorId(Config config) throws Exception {
		Config con = new Config();
		List<Config> listado;
		Query query = manager.createQuery("FROM Config c where c.idConfig = ?1");
		query.setParameter(1, config.getIdConfig());
		listado = (List<Config>)query.getResultList();
		
		if(listado != null && !listado.isEmpty()) {
			con = listado.get(0);
		}
		
		return con;
	}
	
	public Config leerParametro(String parametro) throws Exception{
		List<Config> lista = null;
		Config  config = new Config();
		Query query = manager.createQuery("FROM Config c where c.clave = ?1");
		query.setParameter(1, parametro);
		
		lista = (List<Config>)query.getResultList();
		
		if(lista != null && !lista.isEmpty()){
			config = lista.get(0);
		}
		
		return config;
	}

}
