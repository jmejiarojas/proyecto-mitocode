package pe.cibertec.dao.controller;

import java.io.Serializable;

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
	
	public void pruebaCDI() throws Exception {
		
		Persona persona = new Persona();
		persona.setIdPersona(1);
		persona.setNombres("Julio");
		persona.setApellidos("Mejia Rojas");
		
		personaService.registrar(persona); //Registrando
	}
	
}
