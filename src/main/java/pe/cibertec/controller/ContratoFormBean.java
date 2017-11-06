package pe.cibertec.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import pe.cibertec.models.Config;
import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;
import pe.cibertec.models.Puesto;
import pe.cibertec.service.IConfigService;
import pe.cibertec.service.IContratoService;
import pe.cibertec.service.IPersonaService;
import pe.cibertec.service.IPuestoService;
import pe.cibertec.util.MensajeManager;

@Named
@ViewScoped
public class ContratoFormBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Persona persona;

	@Inject
	private Contrato contrato;

	@Inject
	private Puesto puesto;

	@Inject
	private IContratoService contratoService;

	@Inject
	private IPersonaService personaService;

	@Inject
	private IPuestoService puestoService;
	
	@Inject
	private IConfigService configService;

	List<Persona> lstPersonas;

	List<Puesto> lstPuestos;

	List<Contrato> lstContratos;

	@PostConstruct
	public void init() {
		this.listarPersonas();
		this.listarPuestos();
		
		Contrato cont = (Contrato)Faces.getFlashAttribute("contrato");
		
		if(cont != null){
			//Obtenemos el contrato actual, quizas fue modificado hace milesimas de segundo en otra sesion
			this.leer(cont);
		}else{
			this.leerSueldoMinimo();
		}
	}

	public void leer(Contrato cont) {
		try {
			contrato = contratoService.listarPorId(cont);
			this.persona = contrato.getPersona();
			this.puesto = contrato.getPuesto();
		} catch (Exception e) {
			 MensajeManager.mostrarMensaje("Aviso", e.getMessage(), MensajeManager.ERROR);
			
		}
		
	}

	public void leerSueldoMinimo() {
		try {
			
			InputStream inputStream = ContratoFormBean.class.getClassLoader().getResourceAsStream("/parametros.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			
			String parametro = properties.getProperty("sueldo_minimo");
			Config conf = configService.leerParametro(parametro);
			double salarioMinimo = conf.getValor() != null ? Double.parseDouble(conf.getValor()) : 0.0;
			this.contrato.setSalario(salarioMinimo);
			
		} catch (Exception e) {
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), MensajeManager.ERROR);
		}
	}


	public void listarPersonas() {
		try {
			lstPersonas = personaService.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listarPuestos() {
		try {
			lstPuestos = puestoService.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void registrar() {

		try {
			contrato.setIdContrato(contratoService.generarId(persona));
			contrato.setPersona(persona);
			contrato.setPuesto(puesto);
			contratoService.registrar(contrato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MensajeManager.mostrarMensaje("Aviso", e.getMessage(), MensajeManager.ERROR);
		}
	}
	

	/**
	 * getters & setters
	 */

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public List<Persona> getLstPersonas() {
		return lstPersonas;
	}

	public void setLstPersonas(List<Persona> lstPersonas) {
		this.lstPersonas = lstPersonas;
	}

	public List<Puesto> getLstPuestos() {
		return lstPuestos;
	}

	public void setLstPuestos(List<Puesto> lstPuestos) {

	}

	public List<Contrato> getLstContratos() {
		return lstContratos;
	}

	public void setLstContratos(List<Contrato> lstContratos) {
		this.lstContratos = lstContratos;
	}

}
