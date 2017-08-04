package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pe.cibertec.dao.IPersonaDAO;
import pe.cibertec.models.Persona;

@Stateless
public class PersonaDAOImpl implements IPersonaDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "PersonalPU")
	private EntityManager manager;

	@Override
	public void registrar(Persona persona) throws Exception {
		try {
			manager.persist(persona);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modificar(Persona persona) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Persona> listar() throws Exception {
		List<Persona> lista = null;
		
		try {
			Query query = manager.createQuery("FROM Persona p");
			lista = (List<Persona>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

}
