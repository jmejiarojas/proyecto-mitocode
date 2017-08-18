package pe.cibertec.dao;

import javax.ejb.Local;

import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;

@Local
public interface IContratoDAO extends IDAO<Contrato>{
	
	int generarId(Persona persona);
}
