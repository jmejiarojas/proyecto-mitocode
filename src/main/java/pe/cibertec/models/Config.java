package pe.cibertec.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "config")
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private byte idConfig; // Me soporta 127 numeros, es lo optimo para este caso

	@Column(name = "clave", length = 50, nullable = false)
	private String clave;

	@Column(name = "valor", length = 100, nullable = false)
	private String valor;

	public byte getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(byte idConfig) {
		this.idConfig = idConfig;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
