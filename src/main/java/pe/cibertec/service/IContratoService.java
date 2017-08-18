package pe.cibertec.service;

import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;

public interface IContratoService extends IService<Contrato>{
	int generarId(Persona persona);
}
