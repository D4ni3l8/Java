<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/style.css">
<title>Insert film here</title>
</head>
<body>
<div id="insert-movie">
  <form action="./Insertmovie" method="post">
  	<h2>Insert a movie</h2>
    <label>Title</label>
    <input type="text" id="title" name="title" placeholder="Title"><br>
    <label>Year</label>
    <input type="number" id="year" name="year" placeholder="Year"><br>
    <label>Director</label>
    <input type="text" id="director" name="director" placeholder="director"><br>
    <label>Stars</label>
    <input type="text" id="stars" name="stars" placeholder="Stars"><br>
    <label>Review</label>
    <input type="text" id="review" name="review" placeholder="Review"><br>
 
    <input type="submit" value="Insert film">
  </form>
</div>
</body>
</html>