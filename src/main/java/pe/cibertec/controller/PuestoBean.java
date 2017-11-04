package pe.cibertec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import pe.cibertec.models.Funcion;
import pe.cibertec.models.Puesto;
import pe.cibertec.service.IFuncionService;
import pe.cibertec.service.IPuestoService;

@Named
@ViewScoped
public class PuestoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IPuestoService puestoService;

	@Inject
	private Puesto puesto;

	private List<Puesto> lstPuestos;

	@PostConstruct
	public void init() {
		lstPuestos = new ArrayList<>();
		this.listar();
	}

	public void listar() {
		try {
			lstPuestos = puestoService.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void seleccionar(Puesto pue) throws Exception {
		
		//Enviar por OmniFaces
		Faces.setFlashAttribute("idPuesto", pue.getIdPuesto());

	}

	/**
	 * getters & setters
	 * 
	 * @return
	 */


	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public List<Puesto> getLstPuestos() {
		return lstPuestos;
	}

	public void setLstPuestos(List<Puesto> lstPuestos) {
		this.lstPuestos = lstPuestos;
	}

}
