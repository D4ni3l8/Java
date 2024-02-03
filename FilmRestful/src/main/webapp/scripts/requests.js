
$('#xml').click(function() {

	$('#tablexml').empty(); // clears the table
	//load xml and parse the xml
	$.ajax({
		headers: { "Accept": "application/xml" },
		type: 'GET',
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		dataType: 'xml',
		success: function(xml) {
			var table_headings = '<table class="table table-bordered" id="xmltable">';
			table_headings += '<thead><tr>';
			table_headings += '<th>Title</th><th>Year</th><th>Stars</th><th>Director</th>';
			table_headings += '<th>Reviews</th><th>Update</th><th>Delete</th></tr></thead>';
			table_headings += '<tbody id="tablexml"></tbody></table>';
			$('#xmlfilms').append(table_headings);

			//finds the film tags, goes through them and append them in the table
			$(xml).find('film').each(function() {

				//put new data in the table
				$('#tablexml').append(
					'<tr>' +
					'<td>' + $(this).find("title").text() + '</td>' +
					'<td>' + $(this).find("year").text() + '</td>' +
					'<td>' + $(this).find("stars").text() + '</td>' +
					'<td>' + $(this).find("director").text() + '</td>' +
					'<td>' + $(this).find("review").text() + '</td>' +
					'<td><button type="button" class="btn btn-default btn-primary" data-toggle="modal" data-target="#xmlmodal" onclick="formUpdate(\'' + $(this).find("id").text() + '\', \'' + $(this).find("title").text() + '\', \'' + $(this).find("year").text() + '\', \'' + $(this).find("director").text() + '\', \'' + $(this).find("stars").text() + '\', \'' + $(this).find("review").text() + '\')"><i class="glyphicon glyphicon-pencil"></i></button></td>' +
					'<td><button type="button" class="btn btn-default btn-danger" onclick="deletexml(\'' + $(this).find("id").text() + '\')"><i class="glyphicon glyphicon-trash"></button></td>' +
					'</tr>');

			});

		}
	});
});
$('#json').click(function() {
	// this functions displays data in json format	
	$.ajax({
		headers: { "Accept": "application/json" },
		url: 'http://localhost:8080/FilmRestful/FilmAPI',
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			var table_headings = '<table class="table table-bordered" id="jsontable">';
			table_headings += '<thead><tr>';
			table_headings += '<th>Title</th><th>Year</th><th>Stars</th><th>Director</th>';
			table_headings += '<th>Reviews</th><th>Update</th><th>Delete</th></tr></thead>';
			table_headings += '<tbody id="tablejson"></tbody></table>';
			$('#jsonfilms').append(table_headings);
			for (var i = 0; i < data.length; i++) {
				var row = $('<tr id=' + data[i].id + '><td>' + data[i].title + '</td><td>' + data[i].year + '</td><td>' + data[i].stars + '</td><td>' + data[i].director + '</td><td>' + data[i].review +
					'</td><td><button type="button" class="btn btn-default btn-primary edit" data-toggle="modal" data-target="#jsonmodal" onclick = "formUpdate(\'' + data[i].id + '\', \'' + data[i].title + '\', \'' + data[i].year + '\',\'' + data[i].stars + '\', \'' + data[i].director + '\',\'' + data[i].review + '\')"><i class="glyphicon glyphicon-pencil"></i></button></td>'
					+ '<td><button type="button" class="btn btn-default btn-danger" onclick="deletejson(\'' + data[i].id + '\')"><i class="glyphicon glyphicon-trash"></i></button></td>' + '</tr>');
				$('#tablejson').append(row);

			}

		},
		error: function(textStatus, errorThrown) {
			alert('Error: ' + textStatus + ' - ' + errorThrown);
		}
	});
});
$('#string').click(function() { //this function shows the film table in string format
	$.ajax({
		headers: { "Accept": "text/plain" },
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'GET',
		dataType: 'text',
		success: function(data) {
			var film_data = data.split("$");
			var table_data = '';

			for (var count = 0; count < film_data.length - 1; count++) {
				table_data += "<tr>";

				var cell_data = film_data[count].split("|");
				for (var cell_count = 1; cell_count < cell_data.length; cell_count++) {

					table_data += '<td>' + cell_data[cell_count] + '</td>';
					
				}

				table_data += '<td><button type="button" class="btn btn-default btn-primary" data-toggle="modal" data-target="#stringmodal" onclick="formUpdate(\'' + cell_data[0] + '\', \'' + cell_data[1] + '\', \'' + cell_data[2] + '\', \'' + cell_data[3] + '\', \'' + cell_data[4] + '\', \'' + cell_data[5] + '\')"><i class="glyphicon glyphicon-pencil"></i></button></td>';
				table_data += '<td><button type="button" class="btn btn-default btn-danger" onclick="deletetxt(\'' + cell_data[0] + '\')"><i class="glyphicon glyphicon-trash"></i></button></td>';
				table_data += '</tr>';

			}
			table_data += '</table>';
			console.log(table_data);
			$('#stringbody').html(table_data);


		}
	})

});

var film = {};
$('#insertfilm').click(function() { //this function uses the post method to insert films (json format)
	film.title = $("#title").val();
	film.year = $("#year").val();
	film.director = $("#director").val();
	film.stars = $("#stars").val();
	film.review = $("#review").val();
	var filmObj = JSON.stringify(film);
	console.log(filmObj);

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'POST',
		data: filmObj,
		dataType: "json",
		contentType: 'application/json',
		success: insertSuccessJSON()

	})
})

$('#insertfilmxml').click(function() { //this function uses the post method to insert films (xml format)
	var title = $("#title").val();
	var year = $("#year").val();
	var director = $("#director").val();
	var stars = $("#stars").val();
	var review = $("#review").val();

	var xmlfilm = "<film>";
	xmlfilm += "<title>" + title + "</title>";
	xmlfilm += "<year>" + year + "</year>";
	xmlfilm += "<director>" + director + "</director>";
	xmlfilm += "<stars>" + stars + "</stars>";
	xmlfilm += "<review>" + review + "</review>";
	xmlfilm += "</film>";
	console.log(xmlfilm);

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'POST',
		data: xmlfilm,
		dataType: "application/xml",
		contentType: 'application/xml',
		success: insertSuccessXML()
	})
})


$('#insertfilmtxt').click(function() { // this function inserts new movies in text format
	var title = $("#title").val();
	var year = $("#year").val();
	var stars = $("#stars").val();
	var director = $("#director").val();
	var review = $("#review").val();

	var txtfilm = title + "|" + year + "|" + director + "|" + stars + "|" + review;

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'POST',
		data: txtfilm,
		dataType: "text",
		contentType: 'text/plain',
		success: insertSuccessTXT()
	})
})

function formUpdate(id, title, year, director, stars, review) { // this function pre-populates the update form
	document.getElementById("m_id").value = id;
	document.getElementById("m_title").value = title;
	document.getElementById("m_year").value = year;
	document.getElementById("m_director").value = director;
	document.getElementById("m_stars").value = stars;
	document.getElementById("m_review").value = review;

}

var jsonfilm = {};
$('#jsonupdate').click(function() { 
	jsonfilm.id = $("#m_id").val();
	jsonfilm.title = $("#m_title").val();
	jsonfilm.year = $("#m_year").val();
	jsonfilm.director = $("#m_director").val();
	jsonfilm.stars = $("#m_stars").val();
	jsonfilm.review = $("#m_review").val();
	var filmObj = JSON.stringify(jsonfilm);
	console.log(filmObj);

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'PUT',
		data: filmObj,
		dataType: "json",
		contentType: 'application/json',
		success: updateSuccessJSON()

	})
})

$('#xmlupdate').click(function() { //this function updates the films in xml format
	var id = $("#m_id").val();
	var title = $("#m_title").val();
	var year = $("#m_year").val();
	var director = $("#m_director").val();
	var stars = $("#m_stars").val();
	var review = $("#m_review").val();

	var xmlfilm = "<film>";
	xmlfilm += "<id>" + id + "</id>";
	xmlfilm += "<title>" + title + "</title>";
	xmlfilm += "<year>" + year + "</year>";
	xmlfilm += "<director>" + director + "</director>";
	xmlfilm += "<stars>" + stars + "</stars>";
	xmlfilm += "<review>" + review + "</review>";
	xmlfilm += "</film>";
	console.log(xmlfilm);

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'PUT',
		data: xmlfilm,
		dataType: "xml",
		contentType: 'application/xml',
		success: updateSuccessXML()
	})
})

$('#txtupdate').click(function() { // this function updates the films in text format
	var id = $("#m_id").val();
	var title = $("#m_title").val();
	var year = $("#m_year").val();
	var stars = $("#m_stars").val();
	var director = $("#m_director").val();
	var review = $("#m_review").val();

	var txtobj = id + "|" + title + "|" + year + "|" + director + "|" + stars + "|" + review;

	$.ajax({
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		type: 'PUT',
		data: txtobj,
		dataType: "text",
		contentType: 'text/plain',
		success : updateSuccessTXT()
	})
})


//when inserting has been completed in any of the formats, 
//there will be an alert, and the user will be redirected
function insertSuccessXML() { 
	alert("Film has been inserted.");
	window.location = "http://localhost:8080/FilmRestful/xmlfilms.html";
}

function insertSuccessJSON() {
	alert("Film has been inserted.")
	window.location = "http://localhost:8080/FilmRestful/jsonfilms.html";
}

function insertSuccessTXT() {
	alert("Film has been inserted.")
	window.location = "http://localhost:8080/FilmRestful/txtfilms.html";
}

//when updating has been completed in any of the formats, 
//there will be an alert, and the user will be redirected
function updateSuccessXML() {
	alert("Film has been updated.")
	window.location = "http://localhost:8080/FilmRestful/xmlfilms.html";
}

function updateSuccessJSON() {
	alert("Film has been updated.")
	window.location = "http://localhost:8080/FilmRestful/jsonfilms.html";
}

function updateSuccessTXT() {
	alert("Film has been updated.")
	window.location = "http://localhost:8080/FilmRestful/txtfilms.html";
}

function filmDelete() { //the user will be alerted that the movie is deleted
	alert("Film has been deleted.");
}

function deletejson(id) { //this function deletes the films in json format
	var id = id;
	var obj = { "id": id }
	$.ajax({
		type: 'DELETE',
		data: JSON.stringify(obj),
		headers: { "Content-Type": "application/json" },
		dataType: 'json',
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		success: filmDelete()
		
	})
}

function deletetxt(id) { //this function deletes the films in text format
	var idNum = id;
	$.ajax({
		type: 'DELETE',
		data: idNum,
		headers: { "Content-Type": "text/plain" },
		dataType: 'text',
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		success: filmDelete()

	})
}

function deletexml(idnum) { //this function deletes the films in xml format
	var id = idnum;

	var obj = "<film>" + "<id>" + id + "</id>" + "</film>";
	$.ajax({
		type: 'DELETE',
		headers: { 'Content-Type': 'application/xml' },
		data: obj,
		dataType: 'xml',
		url: "http://localhost:8080/FilmRestful/FilmAPI",
		success: filmDelete()

	})
};
