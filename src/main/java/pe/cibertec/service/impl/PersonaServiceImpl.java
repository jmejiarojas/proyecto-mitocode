package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pe.cibertec.dao.IPersonaDAO;
import pe.cibertec.models.Persona;
import pe.cibertec.service.IPersonaService;

@Named
@RequestScoped
public class PersonaServiceImpl implements IPersonaService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IPersonaDAO dao;
	
	@Override
	public void registrar(Persona persona) throws Exception {
		dao.registrar(persona);
	}

	@Override
	public void modificar(Persona persona) throws Exception {
		dao.modificar(persona);
	}

	@Override
	public List<Persona> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Persona listarPorId(Persona persona) throws Exception {
		return dao.listarPorId(persona);
	}

}
