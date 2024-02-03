<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.Film" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/style.css">
<title>Update film</title>
</head>
<body>

<div id="insert-movie">
  <form action="./Updatefilm" method="post">
  <input type="hidden" name="id" value="${f.getId()}">
  	<h2>Update a movie</h2>
    <label>Title</label>
    <input type="text" id="title" name="title" value="${f.getTitle()}"><br>
    <label>Year</label>
    <input type="number" id="year" name="year" value="${f.getYear()}"><br>
    <label>Director</label>
    <input type="text" id="director" name="director" value="${f.getDirector()}"><br>
    <label>Stars</label>
    <input type="text" id="stars" name="stars" value="${f.getStars()}"><br>
    <label>Review</label>
    <input type="text" id="review" name="review" value="${f.getReview()}"><br>
 
    <input type="submit" value="Update film">
  </form>
</div>
</body>
</html>