package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pe.cibertec.dao.IContratoDAO;
import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;
import pe.cibertec.service.IContratoService;

@Named
@RequestScoped
public class ContratoServiceImpl implements IContratoService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IContratoDAO dao;

	@Override
	public void registrar(Contrato contrato) throws Exception {
		dao.registrar(contrato);
	}

	@Override
	public void modificar(Contrato contrato) throws Exception {
		dao.modificar(contrato);
	}

	@Override
	public List<Contrato> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Contrato listarPorId(Contrato contrato) throws Exception {
		return dao.listarPorId(contrato);
	}

	@Override
	public int generarId(Persona persona) {
		return dao.generarId(persona);
	}

}
