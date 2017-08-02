package pe.cibertec.dao.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import pe.cibertec.models.Persona;
import pe.cibertec.service.IPersonaService;

@Named
@ViewScoped
public class PersonaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private IPersonaService personaService;
	
	//Manejamos una lista de personas
	List<Persona> listaPersonas;
	
	@PostConstruct
	public void init() {
		listar();
	}
	
	private void listar() {
		try {
			listaPersonas = personaService.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pruebaCDI() throws Exception {
		
		Persona persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombres("Julio");
		persona.setApellidos("Mejia Rojas");
		persona.setSexo("M");
		persona.setTelefono("123456789");
		persona.setDireccion("mi casa");
		
		personaService.registrar(persona); //Registrando
	}

	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}
	
}
