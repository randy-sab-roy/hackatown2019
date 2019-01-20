const data = [];

function changeLineType(id, service) {
	data[id] = { service };
	refreshList();
}

function convertRoute(route) {
	route = +route;
	const quotient = Math.floor(route / 10);
	const modulus = route % 10;
	if (modulus === 0) {
		if (quotient % 2 === 0)
			return quotient + "N";
		else
			return quotient + "E";
	} else {
		if (quotient % 2 === 0)
			return quotient + "S";
		else
			return quotient + "W";
	}
}

function busRouteHTML() {
	let html = `<select class="custom-select">`;
	html += Object.keys(routes).map(x => `<option value="${x}">${convertRoute(x)}</option>`).join("");
	html += `</select>`;
	return html;
}

function busStopHTML(route) {
	let html = `<select class="custom-select">`;
	if (route != null) {
		// TODO
	} else {
		html += intersections.map(x => `<option value="${x.id}">${x.name}</option>`).join("");
	}
	html += `</select>`
	return html;
}

function lineToHTML(id, data) {
	let html = `<div class="row p-4">`
		+ `<div class="col-2" style="text-align: center;">`
		+ `<h3 class="font-weight-bold">Ligne ${+id + 1} :</h3>`
		+ `</div>`
		+ `<div class="col-2">`
		+ `	<select class="custom-select" onchange="changeLineType(${id}, this.value);">`
		+ `		<option value="metro" ${(data.service === "metro") ? "selected" : ""}>Métro</option>`
		+ `		<option value="bus" ${(data.service === "bus") ? "selected" : ""}>Bus</option>`
		+ `		<option value="trafic" ${(data.service === "trafic") ? "selected" : ""}>Trafic</option>`
		+ `	</select>`
		+ `</div>`;

	switch (data.service) {
		case "metro":
			html += metroToHTML(data);
			break;
		case "bus":
			html += busToHTML(data);
			break;
		case "trafic":
			html += traficToHTML(data);
			break;
		default:
			break;
	}

	html += `</div>`;
	return html;
}

function metroToHTML(line) {
	return `<div class="col-4">`
		+ `</div>`
		+ `<div class="col-4">`
		+ `</div>`;
}

function busToHTML(data) {
	let html = `<div class="col-4">`;
	html += busRouteHTML();
	html += `</div>`
		+ `<div class="col-4">`;
	html += busStopHTML();
	html += `</div>`;
	return html;
}

function traficToHTML(data) {
	return `<div class="col-4">`
		+ `<input type="text" class="form-control" value="${data.origine || ""}" placeholder="Origin"/>`
		+ `</div>`
		+ `<div class="col-4">`
		+ `<input type="text" class="form-control" value="${data.destination || ""}" placeholder="Destination"/>`
		+ `</div>`;
}

function refreshList() {
	const lines = document.getElementById("lines");
	lines.innerHTML = "";
	data.forEach((x, i) => {
		lines.innerHTML += lineToHTML(i, x);
	});
}

function init() {
	firebase.firestore().collection("boards").doc("7qOUSBuRUMwagevZd8ch").collection("lines").get().then((querySnapshot) => {
		querySnapshot.forEach((doc) => {
			data[doc.id] = doc.data();
			refreshList();
		});
	});
}

init();