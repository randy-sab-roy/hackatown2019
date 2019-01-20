function lineToHTML(id, data) {
	const html = ``;

	switch (data.service) {
		case "metro":
			metroToHTML(data);
			break;
		case "bus":
			busToHTML(data);
			break;
		case "trafic":
			traficToHTML(data);
			break;
		default:
			break;
	}
}

function metroToHTML(line) {
	return `<div class="line">Service : <span>${line.service}</span></div>`;
}

function busToHTML() {

}

function traficToHTML() {

}

function displayLines() {
	firebase.firestore().collection("boards").doc("7qOUSBuRUMwagevZd8ch").collection("lines").get().then((querySnapshot) => {
		querySnapshot.forEach((doc) => {
			document.getElementById("lines").innerHTML += lineToHTML(doc.id(), doc.data());
		});
	});
}

displayLines();