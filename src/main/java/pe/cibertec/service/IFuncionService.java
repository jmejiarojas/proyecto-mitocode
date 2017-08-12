package pe.cibertec.service;

import java.util.List;

import pe.cibertec.models.Funcion;
import pe.cibertec.models.Puesto;

public interface IFuncionService {
	
	List<Funcion> listar(Puesto puesto) throws Exception;
}
