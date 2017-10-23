package pe.cibertec.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
public class ContratoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int idContrato;

	@ManyToOne
	@JoinColumn(name = "idPersona", nullable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "idPuesto", nullable = false)
	private Puesto puesto;

	public int getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(int idContrato) {
		this.idContrato = idContrato;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idContrato;
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((puesto == null) ? 0 : puesto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContratoPK other = (ContratoPK) obj;
		if (idContrato != other.idContrato)
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (puesto == null) {
			if (other.puesto != null)
				return false;
		} else if (!puesto.equals(other.puesto))
			return false;
		return true;
	}

}
