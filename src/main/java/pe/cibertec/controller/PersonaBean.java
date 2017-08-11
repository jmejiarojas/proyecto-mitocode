package pe.cibertec.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;

import pe.cibertec.models.Persona;
import pe.cibertec.models.Telefono;
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

	@Inject
	Telefono telefono;

	// Manejamos una lista de personas
	List<Persona> listaPersonas;

	// Manejamos una lista de Telefonos
	List<Telefono> listaTelefonos;

	private Date fechaSeleccionada;

	private UploadedFile foto;

	private String titulo;

	@PostConstruct
	public void init() {
		listar();
		this.listaTelefonos = new ArrayList<>();
		this.titulo = "Registrar Persona";
	}

	public void seleccionar(Persona per) throws Exception {
		persona = personaService.listarPorId(per);
		Calendar cal = Calendar.getInstance();
		cal.set(persona.getFechaNac().getYear(), persona.getFechaNac().getMonthValue(),
				persona.getFechaNac().getDayOfMonth());
		this.fechaSeleccionada = cal.getTime();
		this.listaTelefonos = persona.getTelefonos();
		
		this.titulo = "Modificar Persona";
	}

	private void listar() {
		try {
			listaPersonas = personaService.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void operar() {

		try {

			if (foto != null) {
				persona.setFoto(foto.getContents());
			}

			if (fechaSeleccionada != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaSeleccionada);

				LocalDate localDate = LocalDate.of(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH));
				persona.setFechaNac(localDate);
			}
			
			persona.setTelefonos(listaTelefonos);
			
			//Para modificar la persona
			if (persona.getIdPersona() > 0) {
				personaService.modificar(persona);
			} else { //Para modificar la persona
				this.titulo = "Registrar Persona";
				personaService.registrar(persona);
			}

			this.listar();

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
	
	public void agregarTelefono() {
		//Instanciamos un objeto de Telefono aca y lo seteamos al valor declarado global
		//para que no haya problemas de duplicidad: (Analizar la logica)
		
		Telefono tel = new Telefono();
		tel.setNumero(telefono.getNumero());
		tel.setPersona(persona);
		this.listaTelefonos.add(tel);
	}
	
	public void removerTelefono(Telefono tel) {
		this.listaTelefonos.remove(tel);
	}
	
	public void limpiarControles() {
		this.persona.setIdPersona(0);
		this.persona.setNombres(null);
		this.persona.setApellidos(null);
		this.persona.setDireccion(null);
		this.persona.setSexo(null);
		//this.persona.getFechaNac(null);
		this.fechaSeleccionada = null;
		this.listaTelefonos = new ArrayList<>();
	}

	/**
	 * getters & setters
	 * @return
	 */
	
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Telefono getTelefono() {
		return telefono;
	}

	public void setTelefono(Telefono telefono) {
		this.telefono = telefono;
	}

	public List<Telefono> getListaTelefonos() {
		return listaTelefonos;
	}

	public void setListaTelefonos(List<Telefono> listaTelefonos) {
		this.listaTelefonos = listaTelefonos;
	}
}
