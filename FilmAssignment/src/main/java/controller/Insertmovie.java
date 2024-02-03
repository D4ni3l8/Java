package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import database.FilmDAO;

/**
 * Servlet implementation class Insertmovie
 */
@WebServlet("/Insertmovie")
public class Insertmovie extends HttpServlet { // This servlet inserts new films
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		FilmDAO dao = new FilmDAO();
		ArrayList<Film> allfilms = dao.getAllFilms();
		request.setAttribute("Insertmovie", allfilms);
		RequestDispatcher rd = request.getRequestDispatcher("insertfilm.jsp"); // gets request from the insertfilm.jsp
																				// file
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
		//
		//Retrieves the values from the insert form and stores the values in variables 
		String title = String.valueOf(request.getParameter("title")); 
		String director = String.valueOf(request.getParameter("director")); 
		Integer year = Integer.valueOf(request.getParameter("year")); 
		String stars = String.valueOf(request.getParameter("stars")); 
		String review = String.valueOf(request.getParameter("review")); 

		FilmDAO dao = new FilmDAO(); 
		Film f = new Film(title, year, director, stars, review); // instantiates object with the variables as arguments

		try {
			dao.insertFilm(f); // this line runs the query from the dao to insert the new film in the database
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./Showfilms"); // when the movie is inserted the user is redirected to the homepage

	}
}
