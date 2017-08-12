package pe.cibertec.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	private IFuncionService funcionService;

	@Inject
	private Funcion funcion;

	@Inject
	private Puesto puesto;

	private List<Puesto> lstPuestos;
	private List<Funcion> lstFunciones;
	
	@PostConstruct
	public void init() {
		lstPuestos = new ArrayList<>();
		lstFunciones = new ArrayList<>();
		this.listar();
	}
	
	public void listar() {
		try {
			lstPuestos = puestoService.listar() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void agregar() {
		Funcion fun = new Funcion();
		fun.setNombre(funcion.getNombre());
		fun.setDescripcion(funcion.getDescripcion());
		fun.setPuesto(puesto);
		this.getLstFunciones().add(fun);
	}
	
	public void seleccionar(Puesto pue) throws Exception {
		puesto =  puestoService.listarPorId(pue);
		this.lstFunciones = funcionService.listar(puesto);
	}
	
	public void operar() throws Exception{
		puesto.setFunciones(lstFunciones);
		
		if(puesto.getIdPuesto() > 0) {
			puestoService.modificar(puesto);
		}else {
			puestoService.registrar(puesto);
		}
		
		this.listar();
	}
	
	public void remover(Funcion fun) {
		this.lstFunciones.remove(fun);
	}
	
	public void limpiarControles() {
		this.puesto.setFunciones(new ArrayList<>());
		this.lstFunciones.clear();
		this.lstFunciones = new ArrayList<>();
		this.puesto.setNombre(null);
		this.puesto.setSalarioBase(0.0);
		this.funcion.setIdFuncion(0);
		this.funcion.setDescripcion(null);
		this.funcion.setNombre(null);
		this.funcion.setPuesto(null);
	}

	/**
	 * getters & setters
	 * @return
	 */

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

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

	public List<Funcion> getLstFunciones() {
		return lstFunciones;
	}

	public void setLstFunciones(List<Funcion> lstFunciones) {
		this.lstFunciones = lstFunciones;
	}

}
