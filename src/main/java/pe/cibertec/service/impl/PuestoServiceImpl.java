package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pe.cibertec.dao.IPuestoDAO;
import pe.cibertec.models.Puesto;
import pe.cibertec.service.IPuestoService;

@Named
@RequestScoped
public class PuestoServiceImpl implements IPuestoService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private IPuestoDAO dao;

	@Override
	public void registrar(Puesto puesto) throws Exception {
		dao.registrar(puesto);
	}

	@Override
	public void modificar(Puesto puesto) throws Exception {
		dao.modificar(puesto);
	}

	@Override
	public List<Puesto> listar() throws Exception {

		return dao.listar();
	}

	@Override
	public Puesto listarPorId(Puesto puesto) throws Exception {
		return dao.listarPorId(puesto);
	}

}
