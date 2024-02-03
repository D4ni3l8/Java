<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="model.Film"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
<title>Movies table</title>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="index.jsp">FilmsDB</a> <a
					href="index.jsp">Home</a> <a href="insertfilm.jsp">Insert new
					film</a>
			</div>

		</div>
	</nav>

	<ul>
		<%
		ArrayList<Film> allFilms = (ArrayList<Film>) request.getAttribute("Showfilms");

		out.write("<table  class='table table-hover'>");
		out.write("<tr>");
		out.write("<th>Title</th>");
		out.write("<th>Year</th>");
		out.write("<th>Director</th>");
		out.write("<th>Stars</th>");
		out.write("<th>Reviews</th>");
		out.write("<th>Update Record</th>");
		out.write("<th>Delete Record</th>");
		out.write("</tr>");

		for (Film f : allFilms) {

			out.write("<tr>");
			out.write("<td>" + f.getTitle() + "</td>");
			out.write("<td>" + f.getYear() + "</td>");
			out.write("<td>" + f.getDirector() + "</td>");
			out.write("<td>" + f.getStars() + "</td>");
			out.write("<td>" + f.getReview() + "</td>");
			out.write("<td><a href=\"./Updatefilm?id=" + f.getId()
			+ "\"><button type='button' class='btn btn-primary'>Update</button></a></td>");
			out.write("<td><a href=\"./Deletefilm?id=" + f.getId()
			+ "\"><button type='button' class='btn btn-danger'>Delete</button></a></td>");
			out.write("</td>");
		}
		out.write("</table>");
		%>


	</ul>
</body>
</html>