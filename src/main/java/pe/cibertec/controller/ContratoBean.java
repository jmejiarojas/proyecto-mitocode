package pe.cibertec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pe.cibertec.models.Contrato;
import pe.cibertec.models.Persona;
import pe.cibertec.models.Puesto;
import pe.cibertec.service.IContratoService;
import pe.cibertec.service.IPersonaService;
import pe.cibertec.service.IPuestoService;

@Named
@ViewScoped
public class ContratoBean implements Serializable {

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

	List<Persona> lstPersonas;

	List<Puesto> lstPuestos;

	List<Contrato> lstContratos;

	@PostConstruct
	public void init() {
		lstPersonas = new ArrayList<>();
		lstPuestos = new ArrayList<>();
		this.listarPersonas();
		this.listarPuestos();
		this.listarContratos();
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
	
	public void listarContratos() {
		try {
			lstContratos = contratoService.listar();
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
			e.printStackTrace();
		}
		this.listarContratos();
	}
	
	public void selecccionar(Contrato con) throws Exception {
		contrato = contratoService.listarPorId(con);
		this.persona = contrato.getPersona();
		this.puesto = contrato.getPuesto();
	}

	public void limpiarControles() {
		this.contrato.setSalario(0.0);
		this.contrato.setPuesto(null);
		this.contrato.setPersona(null);
		this.contrato.setFechaInicio(null);
		this.contrato.setFechaFin(null);
		this.contrato.setEstado("1");
		this.persona = null;
		this.puesto = null;
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
