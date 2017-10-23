package pe.cibertec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pe.cibertec.models.Config;
import pe.cibertec.service.IConfigService;

@Named
@ViewScoped
public class ConfigBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IConfigService configService;

	@Inject
	private Config config;

	private List<Config> lstConfigs;

	@PostConstruct
	public void init() {
		lstConfigs = new ArrayList<>();
		this.listar();
	}

	public void listar() {
		try {
			lstConfigs = configService.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void seleccionar(Config con) throws Exception{
		Thread.sleep(3000); //Para probar el ajaxStatus de PrimeFaces
		config = configService.listarPorId(con);
	}
	
	public void operar() throws Exception{
		if(config.getIdConfig() > 0) {
			configService.modificar(config);
		}else {
			configService.registrar(config);
		}
		
		this.listar();
	}
	
	public void limpiarControles() {
		short id = 0;
		this.config.setIdConfig(id);
		this.config.setClave(null);
		this.config.setValor(null);
	}

	/**
	 *  getters & setters
	 * @return
	 */
	
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public List<Config> getLstConfigs() {
		return lstConfigs;
	}

	public void setLstConfigs(List<Config> lstConfigs) {
		this.lstConfigs = lstConfigs;
	}

}
