package pe.cibertec.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.cibertec.models.Persona;
import pe.cibertec.service.IPersonaService;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/imagen/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	private IPersonaService service;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idPersona = Integer.parseInt(request.getPathInfo().substring(1));
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		try {
			persona = service.listarPorId(persona);
			response.setContentType(getServletContext().getMimeType(persona.getApellidos()));
			response.setContentLength(persona.getFoto().length);
			response.getOutputStream().write(persona.getFoto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
