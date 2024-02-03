package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Film;
import database.FilmDAO;

/**
 * Servlet implementation class Deletefilm
 */
@WebServlet("/Deletefilm")
public class Deletefilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deletefilm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String id = request.getParameter("id"); 
		int newId = Integer.valueOf(id); // this line casts the id to an integer and retrieves the id
		Film f = new Film();
		f.setId(newId); //this sets the id using the setter class setId
		FilmDAO dao = new FilmDAO();
		PrintWriter out = response.getWriter();
		
		
		try {
			dao.deleteFilm(f); // the query from the dao is ran and the film is deleted
			out.write("Film has been deleted"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("./Showfilms"); // when the film is deleted, the user will be redirected to the homepage
	}
}
