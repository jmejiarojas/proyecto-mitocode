package pe.cibertec.dao;

import java.util.List;

import javax.ejb.Local;

import pe.cibertec.models.Persona;

@Local
public interface IPersonaDAO {

	void registrar(Persona persona) throws Exception;
	void modificar(Persona persona) throws Exception;
	List<Persona> listar() throws Exception;
}
