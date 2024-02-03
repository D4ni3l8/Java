package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;
import com.opencsv.CSVWriter;

import database.FilmDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.Film;
import model.FilmList;

/**
 * Servlet implementation class FilmAPI
 */
@WebServlet("/FilmAPI")
public class FilmAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FilmAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // this method shows the films in different formats (json, xml, text)
		//gets the raw data and send it to the request body
		String format = request.getHeader("Accept");
		FilmDAO dao = new FilmDAO();
		ArrayList<Film> allFilms = dao.getAllFilms();
		response.setContentType("application/json");
		request.setAttribute("films", allFilms);

		if ("application/json".equals(format)) { // this if statement outputs data in JSON
			PrintWriter out = response.getWriter();
			Gson gson = new Gson();
			String json = gson.toJson(allFilms);
			out.write(json);
			out.close();
		} else if ("application/xml".equals(format)) { // this else if statement outputs data in XML
			JAXBContext context;
			try {
				FilmList f = new FilmList(allFilms);
				context = JAXBContext.newInstance(FilmList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				PrintWriter out = response.getWriter();
				m.marshal(f, out);
				out.close();

			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if ("text/plain".equals(format)) { // this else if statement outputs data in TEXT format

			try {
				response.setContentType("text/plain"); 
				StringWriter stringWriter = new StringWriter();
				ArrayList<Film> films = allFilms;
				for (int i = 0; i < films.size(); i++) { // this loop shows the format on how each film should be separated
					stringWriter.append(films.get(i).getId() + "|" + films.get(i).getTitle() + "|"
							+ films.get(i).getYear() + "|" + films.get(i).getDirector() + "|" + films.get(i).getStars()
							+ "|" + films.get(i).getReview() + "$");
				}
				PrintWriter out = response.getWriter();
				out.write(stringWriter.toString()); 
				out.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // this method inserts new films
		// TODO Auto-generated method stub
		//gets the raw data and send it to the request body
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		PrintWriter out = response.getWriter();
		Film f = new Film();

		if ("application/xml".equals(dataFormat)) { // converts the java array to an xml object
			try {
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

			} catch (JAXBException e) {
				e.printStackTrace();
			}

		} else if ("application/json".equals(dataFormat)) { // this else if statement converts 
			Gson gson = new Gson();							// the java array is converted to a json object
			String JSONfilm = data;
			f = gson.fromJson(JSONfilm, Film.class);

		} else if ("text/plain".equals(dataFormat)) {

			f = new Film();
			String[] split = data.split("\\|"); //the data is split with the "|" symbol

			for (String s : split) { 
				System.out.println(s);
			}
			
			// gives each data their index position
			String title = split[0];
			f.setTitle(title);
			String yearstr = split[1];
			int year = Integer.parseInt(yearstr);
			f.setYear(year);
			String director = split[2];
			f.setDirector(director);
			String stars = split[3];
			f.setStars(stars);
			String review = split[4];
			f.setReview(review);

		}

		FilmDAO dao = new FilmDAO();
		try {

			dao.insertFilm(f); // this line inserts the new film in the database
			out.write("Film has been inserted");
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // this method is used to update films
		//gets the raw data and send it to the request body
		FilmDAO dao = new FilmDAO();
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Film f = new Film();

		if ("application/xml".equals(dataFormat)) { //converts the java array to an xml object
			try {
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

			} catch (JAXBException e) {
				e.printStackTrace();
			}

		} else if ("application/json".equals(dataFormat)) { //converts java array to a json object
			Gson gson = new Gson();
			String JSONfilm = data;
			f = gson.fromJson(JSONfilm, Film.class);

		} else if ("text/plain".equals(dataFormat)) { //converts java array to a text object
			
			// gives each data their index position
			f = new Film();
			String[] split = data.split("\\|");
			String idstr = split[0];
			int id = Integer.parseInt(idstr);
			f.setId(id);
			String title = split[1];
			f.setTitle(title);
			String yearstr = split[2];
			int year = Integer.parseInt(yearstr);
			f.setYear(year);
			String director = split[3];
			f.setDirector(director);
			String stars = split[4];
			f.setStars(stars);
			String review = split[5];
			f.setReview(review);

		}

		try { // this try/catch updates film in the database
			dao.updateFilm(f); 
			out.write("Film has been updated.");
			out.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { // this method deletes the film by retrieving the id of the selected
		
		//gets the raw data and send it to the request body
		FilmDAO dao = new FilmDAO();
		PrintWriter out = response.getWriter();
		String dataFormat = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		Film f = new Film();

		if ("application/xml".equals(dataFormat)) { //converts java array to xml object
			try {
				JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Film.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				f = (Film) jaxbUnmarshaller.unmarshal(new StringReader(data));

			} catch (JAXBException e) {
				e.printStackTrace();
			}

		} else if ("application/json".equals(dataFormat)) { //converts java array to json object
			Gson gson = new Gson();
			String JSONfilm = data;
			f = gson.fromJson(JSONfilm, Film.class);
		} else if ("text/plain".equals(dataFormat)) { //converts java array to text object

			f = new Film();
			String[] split = data.split("\\|");
			String idstr = split[0];
			int id = Integer.parseInt(idstr);
			f.setId(id);

		}

		try { // this try/catch deletes the film
			dao.deleteFilm(f);
			out.write("Film has been deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
