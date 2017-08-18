package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pe.cibertec.dao.IContratoDAO;
import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;

@Stateless
public class ContratoDAOImpl implements IContratoDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void registrar(Contrato contrato) throws Exception {
		manager.persist(contrato);

		Query query = manager.createQuery(
				"UPDATE Contrato c SET c.estado = '0' WHERE c.persona.idPersona = ?1 AND c.idContrato <> ?2");
		query.setParameter(1, contrato.getPersona().getIdPersona());
		query.setParameter(2, contrato.getIdContrato());
		query.executeUpdate();
	}

	@Override
	public void modificar(Contrato contrato) throws Exception {
		manager.merge(contrato);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contrato> listar() throws Exception {
		List<Contrato> listado;
		Query query = manager.createQuery("FROM Contrato c where c.estado = '1'");
		listado = (List<Contrato>) query.getResultList();

		return listado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contrato listarPorId(Contrato contrato) throws Exception {
		List<Contrato> listado;
		Contrato cont = new Contrato();

		Query query = manager.createQuery("FROM Contrato c where c.idContrato= ?1");
		query.setParameter(1, contrato.getIdContrato());
		listado = (List<Contrato>) query.getResultList();

		if (listado != null && !listado.isEmpty()) {
			cont = listado.get(0);
		}

		return cont;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized int generarId(Persona persona) {
		int id = 0;
		List<Contrato> lista = new ArrayList<>();
		Query query = manager.createQuery("FROM Contrato c where c.persona.idPersona = ?1");
		query.setParameter(1, persona.getIdPersona());

		lista = (List<Contrato>) query.getResultList();

		if (lista != null && !lista.isEmpty()) {
			id = lista.get(0).getIdContrato() + 1;
		} else {
			id = 1;
		}

		return id;
	}

}
