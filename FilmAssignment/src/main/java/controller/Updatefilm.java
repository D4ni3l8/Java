package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import database.FilmDAO;

/**
 * Servlet implementation class Updatefilm
 */
@WebServlet("/Updatefilm")
public class Updatefilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Updatefilm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		FilmDAO dao = new FilmDAO();
		String id = request.getParameter("id");
		int newId = Integer.valueOf(id);

		Film f = dao.getFilmByID(newId);
		request.setAttribute("f", f); // the "f" comes from the update.jsp page
		RequestDispatcher rd = request.getRequestDispatcher("updatefilm.jsp"); // gets request from the update.jsp file
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		// Retrieves the values from the update form and stores the values in variables 
		int id = Integer.valueOf(request.getParameter("id"));
		String title = String.valueOf(request.getParameter("title")); 
		String director = String.valueOf(request.getParameter("director"));
		Integer year = Integer.valueOf(request.getParameter("year")); 
		String stars = String.valueOf(request.getParameter("stars")); 
		String review = String.valueOf(request.getParameter("review")); 

		FilmDAO d = new FilmDAO(); //instantiates object with the variables as arguments
		PrintWriter out = response.getWriter();
		Film f = new Film(id, title, year, director, stars, review);
		f.setId(id);

		try {
			d.updateFilm(f); // this line update film in the database
			out.write("Film has been updated.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./Showfilms"); // when the movie is inserted the user is redirected to the homepage
	}

}
