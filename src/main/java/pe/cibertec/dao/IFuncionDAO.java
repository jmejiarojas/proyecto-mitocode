package pe.cibertec.dao;

import java.util.List;

import javax.ejb.Local;

import pe.cibertec.models.Funcion;
import pe.cibertec.models.Puesto;

//No vamos a heredar de IDAO, pues solo vamos a usar del negocio}
//una logica diferente.
@Local
public interface IFuncionDAO /*extends IDAO<Funcion>*/{

	List<Funcion> listar(Puesto puesto) throws Exception;
}
