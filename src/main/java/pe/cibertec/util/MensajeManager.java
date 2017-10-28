package pe.cibertec.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensajeManager {

	public static final int INFO = 1, WARN = 2, ERROR = 3, FATAL = 4;

	public synchronized static void mostrarMensaje(String titulo, String cuerpo, int severidad) {
		switch (severidad) {
		case INFO:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, cuerpo));
			break;
		case WARN:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, cuerpo));
			break;
		case ERROR:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, cuerpo));
			break;
		case FATAL:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, cuerpo));
			break;
		}
	}
}
