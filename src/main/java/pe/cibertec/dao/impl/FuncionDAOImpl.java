package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pe.cibertec.dao.IFuncionDAO;
import pe.cibertec.models.Funcion;
import pe.cibertec.models.Puesto;

@Stateless
public class FuncionDAOImpl implements IFuncionDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "PersonalPU")
	EntityManager manager;

	@Override
	public List<Funcion> listar(Puesto puesto) throws Exception {
		
		List<Funcion> funciones = null;
		Query query = manager.createQuery("FROM Funcion f where f.puesto.idPuesto = ?1");

		query.setParameter(1, puesto.getIdPuesto());
		funciones = (List<Funcion>)query.getResultList();

		return funciones;
	}

}
