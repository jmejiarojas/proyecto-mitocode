package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pe.cibertec.dao.IPuestoDAO;
import pe.cibertec.models.Puesto;

@Stateless
public class PuestoDAOImpl implements IPuestoDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void registrar(Puesto puesto) throws Exception {
		manager.persist(puesto);
	}

	@Override
	public void modificar(Puesto puesto) throws Exception {
		manager.merge(puesto);
		
	}

	@Override
	public List<Puesto> listar() throws Exception {
		List<Puesto> listado = null;
		listado = (List<Puesto>)manager.createQuery("From Puesto p").getResultList();
		return listado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Puesto listarPorId(Puesto puesto) throws Exception {
		Puesto p = new Puesto();
		List<Puesto> listado = new ArrayList<>();
		Query query =  manager.createQuery("FROM Puesto p where p.idPuesto = ?1");
		query.setParameter(1,puesto.getIdPuesto());
		
		listado = (List<Puesto>) query.getResultList();
		
		if(listado != null  && !listado.isEmpty()) {
			p = listado.get(0);
		}
		
		return p;
	}

}
