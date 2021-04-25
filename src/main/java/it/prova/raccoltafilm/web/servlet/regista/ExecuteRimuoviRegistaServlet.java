package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.service.RegistaService;

@WebServlet("/ExecuteRimuoviRegistaServlet")
public class ExecuteRimuoviRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idRegistaParam = request.getParameter("idRegista");

		RegistaService serviceRegista = it.prova.raccoltafilm.service.MyServiceFactory.getRegistaServiceInstance();

		if (!NumberUtils.isCreatable(idRegistaParam)) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			request.setAttribute("errorMessage", "Attenzione si è verificato un erroreeeee.");
			request.getRequestDispatcher("regista/list.jsp").forward(request, response);
			return;
		}

		try {
			Regista registaDaEliminare = serviceRegista.caricaSingoloElementoConFilms(Long.parseLong(idRegistaParam));
			if (!registaDaEliminare.getFilms().isEmpty()) {
				request.setAttribute("errorMessage", "Non posso eliminare registi con film associati.");
				request.setAttribute("registi_list_attribute", serviceRegista.listAllElements());
				request.getRequestDispatcher("regista/list.jsp").forward(request, response);
				return;

			}
			serviceRegista.rimuovi(registaDaEliminare);
			
			request.setAttribute("registi_list_attribute", serviceRegista.listAllElements());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("regista/list.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("regista/list.jsp").forward(request, response);
	}

}
