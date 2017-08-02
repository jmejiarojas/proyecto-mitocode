package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pe.cibertec.dao.IPersonaDAO;
import pe.cibertec.models.Persona;

@Named
@RequestScoped
public class PersonaDAOImpl implements IPersonaDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersonalPSQLPU");
	EntityManager manager = factory.createEntityManager();

	@Override
	public void registrar(Persona persona) throws Exception {
		//System.out.println("Registrando la persona");
		try {
			manager.getTransaction().begin(); //Indicamos que vamos a iniciar la transaccion
			manager.persist(persona);
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally {
			manager.close();
			factory.close();
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
			//lista = (List<Persona>) manager.createNativeQuery("SELECT * FROM PERSONA");
			lista = (List<Persona>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			manager.close();
			factory.close();
		}
		return lista;
	}

}
