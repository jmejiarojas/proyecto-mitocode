package pe.cibertec.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(ContratoPK.class)
@Table(name = "contrato")
public class Contrato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int idContrato;

	@Id
	private Persona persona;

	@Id
	private Puesto puesto;

	@Column(name = "fechaInicio", nullable = false)
	private LocalDate fechaInicio;

	@Column(name = "fechaFin", nullable = false)
	private LocalDate fechaFin;

	@Column(name = "salario", columnDefinition = "Decimal(7,2)", nullable = false)
	private double salario;

	@Column(name = "estado", columnDefinition = "char(1)", nullable = false)
	private String estado = "1";

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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Contrato [idContrato=" + idContrato + ", persona=" + persona + ", puesto=" + puesto + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", salario=" + salario + "]";
	}

}
