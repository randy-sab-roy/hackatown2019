const data = [];

function updateDatabase() {
	const lines = firebase.firestore().collection("boards").doc("7qOUSBuRUMwagevZd8ch").collection("lines");
	data.forEach((x, i) => {
		lines.doc(i+"").set(x);
	});
}

function changeLineType(id, service) {
	data[id] = { service };
	refreshList();
}

function changeBusRoute(id, route) {
	data[id].route = route;
	refreshList();
}

function changeBusStop(id, stop) {
	data[id].intersection = +stop;
}

function changeOrigin(id, origin) {
	data[id].origine = origin;
}

function changeDestination(id, destination) {
	data[id].destination = destination;
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

function busRouteHTML(id, route) {
	let html = `<select class="custom-select" onchange="changeBusRoute(${id}, this.value)">`;
	html += Object.keys(routes).map(x => `<option value="${x}" ${(x === route) ? "selected" : ""}>${convertRoute(x)}</option>`).join("");
	html += `</select>`;
	return html;
}

function busStopHTML(id, route, intersection) {
	let html = `<select class="custom-select" onchange="changeBusStop(${id}, this.value)">`;
	if (route != null) {
		html += routes[route].map(x => {
			const intersection = intersections.find(y => y.id === x);
			return `<option value="${intersection.id}">${intersection.name}</option>`;
		}).join("");
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
		+ `		<option value="off" ${(data.service === "off") ? "selected" : ""}>Off</option>`
		+ `	</select>`
		+ `</div>`;

	switch (data.service) {
		case "metro":
			html += metroOrOffToHTML(data);
			break;
		case "bus":
			html += busToHTML(id, data);
			break;
		case "trafic":
			html += traficToHTML(id, data);
			break;
		case "off":
			html += metroOrOffToHTML(data);
			break;
		default:
			break;
	}

	html += `</div>`;
	return html;
}

function metroOrOffToHTML(line) {
	return `<div class="col-4">`
		+ `</div>`
		+ `<div class="col-4">`
		+ `</div>`;
}

function busToHTML(id, data) {
	let html = `<div class="col-4">`;
	html += busRouteHTML(id, data.route);
	html += `</div>`
		+ `<div class="col-4">`;
	html += busStopHTML(id, data.route, data.intersection);
	html += `</div>`;
	return html;
}

function traficToHTML(id, data) {
	return `<div class="col-4">`
		+ `<input type="text" class="form-control" value="${data.origine || ""}" placeholder="Origin" onchange="changeOrigin(${id}, this.value)"/>`
		+ `</div>`
		+ `<div class="col-4">`
		+ `<input type="text" class="form-control" value="${data.destination || ""}" placeholder="Destination" onchange="changeDestination(${id}, this.value)"/>`
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