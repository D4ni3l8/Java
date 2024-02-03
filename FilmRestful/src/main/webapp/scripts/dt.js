function waitForElm(selector) { //mutation observer api to implement datatables
	return new Promise(resolve => {
		if (document.querySelector(selector)) {
			return resolve(document.querySelector(selector));
		}

		const observer = new MutationObserver(mutations => {
			if (document.querySelector(selector)) {
				resolve(document.querySelector(selector));
				observer.disconnect();
			}
		});

		observer.observe(document.body, {
			childList: true,
			subtree: true
		});
	});
}

waitForElm('#jsontable').then((elm) => { //this function implements datatables on the xml table
	$("#jsontable").DataTable({
		"paging": true,
		"searching": true
	});

	console.log('Element is ready');
	console.log(elm.textContent);
});

waitForElm('#xmltable').then((elm) => { //this function implements datatables on the json table
	$("#xmltable").DataTable({
		"paging": true,
		"searching": true
	});

	console.log('Element is ready');
	console.log(elm.textContent);
});

/*waitForElm('#stringtable').then((elm) => {
	$("#stringtable").DataTable({
		"paging": true,
		"searching": true
	});

	console.log('Element is ready');
	console.log(elm.textContent);
});*/
