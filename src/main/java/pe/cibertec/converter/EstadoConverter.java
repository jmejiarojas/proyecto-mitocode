package pe.cibertec.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoConverter")
public class EstadoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		String tipo = "";
		
		if(value != null) {
			tipo = String.valueOf(value);
			switch (tipo) {
			case "1":
				tipo = "ACTIVO";
				break;
			case "0":
				tipo = "INACTIVO";
				break;
			default:
				tipo = "NO VALIDO";
				break;
			}
		}
		return tipo;
	}

}
