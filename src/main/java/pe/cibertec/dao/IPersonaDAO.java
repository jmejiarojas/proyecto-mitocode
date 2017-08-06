package pe.cibertec.dao;

import javax.ejb.Local;

import pe.cibertec.models.Persona;

@Local
public interface IPersonaDAO extends IDAO<Persona> {
	
	void listarWS();
}
