package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import pe.cibertec.dao.IFuncionDAO;
import pe.cibertec.models.Funcion;
import pe.cibertec.models.Puesto;
import pe.cibertec.service.IFuncionService;

@Named
public class FuncionServiceImpl implements IFuncionService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private IFuncionDAO dao;
	
	@Override
	public List<Funcion> listar(Puesto puesto) throws Exception {
		return dao.listar(puesto);
	}

}
