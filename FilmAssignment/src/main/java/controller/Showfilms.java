package controller;

import java.io.IOException;
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
 * Servlet implementation class Showfilms
 */
@WebServlet("/Showfilms")
public class Showfilms extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // this method displays all the films
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		FilmDAO dao = new FilmDAO();
		ArrayList<Film> allfilms = dao.getAllFilms();
		request.setAttribute("Showfilms", allfilms);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); // gets request from the index.jsp file
		rd.include(request, response);
	}
}
