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
import pe.cibertec.util.MensajeManager;

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

	private String accion;

	@PostConstruct
	public void init() {
		this.accion = "Registrar";
		lstConfigs = new ArrayList<>();
		this.listar();
	}

	public void listar() {
		try {
			lstConfigs = configService.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), MensajeManager.ERROR);
		}
	}

	public void seleccionar(Config con) throws Exception {
		config = configService.listarPorId(con);
		this.accion = "Modificar";
	}

	public void operar() {
		
		try {
			if (config.getIdConfig() > 0) {
				configService.modificar(config);
				MensajeManager.mostrarMensaje("Aviso", "Modificacion exitosa", MensajeManager.INFO);
			} else {
				configService.registrar(config);
				MensajeManager.mostrarMensaje("Aviso", "Registro exitoso", MensajeManager.INFO);
			}
			
			this.listar();
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), MensajeManager.ERROR);
		}finally {
			this.limpiarControles();
		}

		
	}

	public void limpiarControles() {
		short id = 0;
		this.config.setIdConfig(id);
		this.config.setClave(null);
		this.config.setValor(null);
		this.accion = "Registrar";
	}

	/**
	 * getters & setters
	 * 
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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
