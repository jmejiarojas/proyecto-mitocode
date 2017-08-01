package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
	@Inject
	private IPersonaDAO dao;
	
	@Override
	public void registrar(Persona persona) throws Exception {
		dao.registrar(persona);
	}

	@Override
	public void modificar(Persona persona) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Persona> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
