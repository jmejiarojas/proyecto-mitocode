package pe.cibertec.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import pe.cibertec.models.Persona;
import pe.cibertec.service.IPersonaService;

@Named
@ViewScoped
public class PersonaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IPersonaService personaService;

	@Inject
	Persona persona;

	// Manejamos una lista de personas
	List<Persona> listaPersonas;

	private Date fechaSeleccionada;

	private UploadedFile foto;

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

	public void registrar() {

		try {
			
			if(foto != null) {
				persona.setFoto(foto.getContents());
			}

			if (fechaSeleccionada != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaSeleccionada);

				LocalDate localDate = LocalDate.of(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH));
				persona.setFechaNac(localDate);
			}
			personaService.registrar(persona);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void modificar() {
		try {
			personaService.modificar(persona);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Date getFechaSeleccionada() {
		return fechaSeleccionada;
	}

	public void setFechaSeleccionada(Date fechaSeleccionada) {
		this.fechaSeleccionada = fechaSeleccionada;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

}
