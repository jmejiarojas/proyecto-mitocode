package pe.cibertec.service;

import java.util.List;

import pe.cibertec.models.Persona;

public interface IPersonaService {
	void registrar(Persona persona) throws Exception;
	void modificar(Persona persona) throws Exception;
	List<Persona> listar() throws Exception;
}
