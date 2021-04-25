package it.prova.raccoltafilm.web.servlet.film;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.prova.raccoltafilm.model.Film;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteUpdateFilmServlet")
public class ExecuteUpdateFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUpdateFilmServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idParam = request.getParameter("idFilm");
		String titoloParam = request.getParameter("titolo");
		String genereParam = request.getParameter("genere");
		String minutiDurataParam = request.getParameter("minutiDurata");
		String dataPubblicazioneParam = request.getParameter("dataPubblicazione");
		String registaIdParam = request.getParameter("regista.id");

		// preparo un bean (che mi serve sia per tornare in pagina
		// che per inserire) e faccio il binding dei parametri
		Film filmInstance = UtilityForm.createFilmFromParams(titoloParam, genereParam, minutiDurataParam,
				dataPubblicazioneParam, registaIdParam);
		filmInstance.setId(Long.parseLong(idParam));

		try {
			if (!UtilityForm.validateFilmBean(filmInstance)) {
				request.setAttribute("errorMessage", "Attenzione sono presenti errori di validazione");
				request.setAttribute("film_da_modificare", filmInstance);
				request.setAttribute("registi_list_attribute",
						MyServiceFactory.getRegistaServiceInstance().listAllElements());
				request.getRequestDispatcher("/film/edit.jsp").forward(request, response);
				return;
			}
			MyServiceFactory.getFilmServiceInstance().aggiorna(filmInstance);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/film/edit.jsp").forward(request, response);
			return;
		}

		// andiamo ai risultati
		// uso il sendRedirect con parametro per evitare il problema del double save on
		// refresh
		response.sendRedirect("ExecuteListFilmServlet?operationResult=SUCCESS");

	}

}
