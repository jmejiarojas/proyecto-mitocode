package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
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
public class PersonaDAOImpl implements IPersonaDAO, Serializable {

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
		manager.merge(persona); //Por JPA no esta actualizando la foto
		
		//Si es que hay una foto actulizamos la Persona con la nueva foto.
		if(persona.getFoto() != null && persona.getFoto().length >0){
			Query query = manager.createQuery("UPDATE Persona SET foto = ?1 WHERE idPersona = ?2");
			query.setParameter(1, persona.getFoto());
			query.setParameter(2, persona.getIdPersona());
			query.executeUpdate();
		}
	}

	@Override
	public List<Persona> listar() throws Exception {
		List<Persona> lista = null;

		try {
			Query query = manager.createQuery("FROM Persona p");
			lista = (List<Persona>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Persona listarPorId(Persona persona) throws Exception {
		Persona per = new Persona();
		List<Persona> lista = new ArrayList<>();

		try {
			Query query = manager.createQuery("FROM Persona p where p.idPersona = ?1");
			query.setParameter(1, persona.getIdPersona());
			lista = (List<Persona>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				per = lista.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return per;
	}

	@Override
	public void listarWS() {
		// TODO Auto-generated method stub
	}

}
