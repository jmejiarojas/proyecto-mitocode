package pe.cibertec.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import pe.cibertec.dao.IConfigDAO;
import pe.cibertec.models.Config;
import pe.cibertec.service.IConfigService;

@Named
@RequestScoped
public class ConfigServiceImpl implements IConfigService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IConfigDAO dao;

	@Override
	public void registrar(Config config) throws Exception {
		dao.registrar(config);
	}

	@Override
	public void modificar(Config config) throws Exception {
		dao.modificar(config);
	}

	@Override
	public List<Config> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Config listarPorId(Config config) throws Exception {
		return dao.listarPorId(config);
	}

}
