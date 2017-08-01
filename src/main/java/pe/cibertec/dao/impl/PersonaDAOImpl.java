package pe.cibertec.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pe.cibertec.dao.IPersonaDAO;
import pe.cibertec.models.Persona;

@Named
@RequestScoped
public class PersonaDAOImpl implements IPersonaDAO, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void registrar(Persona persona) throws Exception {
		System.out.println("Registrando la persona");
		
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
