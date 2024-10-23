import validator from "./validator.js";

"use strict";

const graph = document.getElementById("graph");
const context = graph.getContext("2d");
const margin = 15;
let hasUserInputR = false;

let savedData = [];

window.onload = function () {
    drawGraph(3);

    savedData = loadData();
    let savedLen = savedData.length;
    console.log("Length " + savedLen);
    for (let i = 0; i < savedLen; ++i) {
        let xVal = savedData[i].x;
        let yVal = savedData[i].y;
        let rVal = savedData[i].r;
        let res = savedData[i].res;
        console.log(xVal + " " + yVal + " " + rVal + " " + res)
        drawPoint(xVal, yVal, rVal, res);
    }

}

const saveData = (points) => {
	window.localStorage.setItem('Points', JSON.stringify(points));
};

const loadData = () => {
	const savedData = window.localStorage.getItem('Points');
	return savedData ? JSON.parse(savedData) : [];
};

function drawPoint(x, y, r, hit) {
    let xVal = parseFloat(x)/parseFloat(r);
    let yVal = parseFloat(y)/parseFloat(r);
    let grX = graph.width*(1/2 + xVal / 3);
    let grY = graph.height*(1/2 - yVal / 3);
    console.log("DRAWING ON " + grX + " " + grY);
    context.fillStyle = hit ? "green" : "red";
    context.beginPath();
    context.arc(grX, grY, 5, 0, Math.PI * 2);
    context.fill();
}

function drawAxis(context, fromX, fromY, toX, toY) {
    const headLength = 10;
    const angle = Math.atan2(toY - fromY, toX - fromX);

    context.beginPath();
    context.moveTo(fromX, fromY);
    context.lineTo(toX, toY);
    context.lineTo(toX - headLength * Math.cos(angle - Math.PI / 6), toY - headLength * Math.sin(angle - Math.PI / 6));
    context.moveTo(toX, toY);
    context.lineTo(toX - headLength * Math.cos(angle + Math.PI / 6), toY - headLength * Math.sin(angle + Math.PI / 6));
    context.stroke();
}

let grLeft = graph.offsetLeft + graph.clientLeft;
let grTop = graph.offsetTop + graph.clientTop;
console.log(grLeft + " " + grTop);

graph.addEventListener('click', function() {
    let clickX = event.clientX - graph.getBoundingClientRect().left;
    let clickY = event.clientY - graph.getBoundingClientRect().top;
    let xSize = graph.width;
    let ySize = graph.height;
    console.log("clicked! On " + clickX + " " + clickY);
    console.log("X " + graph.getBoundingClientRect().left + " Y " + graph.getBoundingClientRect().top);
    console.log("PageX " + event.pageX + " PageY " + event.pageY);
    const rVal = document.getElementById("r").value;
    const xVal = (Math.round((clickX-xSize/2)*parseFloat(rVal)*3000000/xSize)/1000000.0).toString();
    const yVal = (Math.round((ySize/2-clickY)*parseFloat(rVal)*3000000/ySize)/1000000.0).toString();
    console.log(xVal, yVal, rVal);
        const url = new URL('controller', window.location.href);
        fetch(url.href, {
    			method: 'POST',
    			headers: {
    				'Content-Type': 'application/json'
    			},
    			body: JSON.stringify({
    				"x": xVal,
    				"y": yVal,
    				"r": rVal,
                    "agent": navigator.userAgent
    			})
    		}).then(response => response.json()).then(data => {
    			console.log("SUCCESS! ", data);
    			const tbody = document.querySelector('#prevData');
    			const newRow = document.createElement('tr');
    			const tx = document.createElement('td');
    			const ty = document.createElement('td');
    			const tr = document.createElement('td');
    			const tres = document.createElement('td');
    			const textime = document.createElement('td');
    			const tservtime = document.createElement('td');
    			tx.textContent = xVal;
    			ty.textContent = yVal;
    			tr.textContent = rVal;
    			tres.textContent = data.res;
    			textime.textContent = data.exTime;
    			let localDate = new Date(data.servTime);
    			tservtime.textContent = localDate.toLocaleTimeString();
    			newRow.appendChild(tx);
    			newRow.appendChild(ty);
    			newRow.appendChild(tr);
    			newRow.appendChild(tres);
    			newRow.appendChild(textime);
    			newRow.appendChild(tservtime);
    			tbody.appendChild(newRow);
    			drawPoint(xVal, yVal, rVal, data.res);
    			savedData.push({ x: xVal, y: yVal, r: rVal, res: data.res});
                saveData(savedData);
    		}).catch((error) => {
    			console.error("ERROR! ", error);
    		});
    }, false);

function drawGraph(R) {
    let width = graph.width;
    let height = graph.height;
    console.log("w: " + width + " h: " + height);
    context.clearRect(0, 0, width, height);
    context.font = "14px Arial";
    context.strokeStyle = "black";
    context.lineWidth = 1;
    context.beginPath();
    drawAxis(context, margin, height/2, width-margin, height/2);
    drawAxis(context, width/2, height-margin, width/2, margin);
    context.fillStyle = "#0000FF10"; // blue with 10% opacity
    context.beginPath();
    context.moveTo(width / 2, height / 2);
    context.lineTo(width / 2, height / 6);
    context.lineTo(2*width / 3, height / 2);
    context.closePath();
    context.fill();
    context.strokeStyle = "#0000FF";
    context.stroke();
    context.fillStyle = "#FFFF0010"; // yellow with 10% opacity
    context.fillRect(width / 2, height / 2, width/3, height/6);
    context.strokeStyle = "#FFFF00";
    context.fillRect(width / 2, height / 2, width/3, height/6);
    context.fillStyle = "#39FF1410"; // green with 10% opacity
    context.beginPath();
    context.arc(width / 2, height / 2, height/3, Math.PI, 1.5*Math.PI);
    context.lineTo(width / 2, height / 2);
    context.closePath();
    context.fill();
    context.strokeStyle = "#39FF14";
    context.beginPath();
    context.arc(width / 2, height / 2, height/3, Math.PI, 1.5*Math.PI);
    context.stroke();
    context.fillStyle = "white";
    const labelR = hasUserInputR ? R.toString() : "R";
    const labelRHalf = hasUserInputR ? (R / 2).toString() : "R/2";
    context.fillText(labelR, width / 2 + 100 - 5, height / 2 + 15);
    context.fillText(labelRHalf, width / 2 + 50 - 12, height / 2 + 15);
    context.fillText('-' + labelR, width / 2 - 100 - 10, height / 2 + 15);
    context.fillText('-' + labelRHalf, width / 2 - 50 - 15, height / 2 + 15);
    context.fillText(labelR, width / 2 + 10, height / 2 - 100 + 5);
    context.fillText(labelRHalf, width / 2 + 10, height / 2 - 50 + 5);
    context.fillText('-' + labelR, width / 2 + 10, height / 2 + 100 + 5);
    context.fillText('-' + labelRHalf, width / 2 + 10, height / 2 + 50 + 5);
    context.fillStyle = "white";
    context.strokeStyle = "#000000";
    const tickLength = 10; // Length of the tick marks
    for (let tickValue = -2; tickValue <= 2; tickValue++) {
        const xTickPosition = width / 2 + tickValue * 50;
        context.beginPath();
        context.moveTo(xTickPosition, height / 2 - tickLength / 2);
        context.lineTo(xTickPosition, height / 2 + tickLength / 2);
        context.stroke();
    }
    for (let tickValue = -2; tickValue <= 2; tickValue++) {
        const yTickPosition = height / 2 - tickValue * 50;
        context.beginPath();
        context.moveTo(width / 2 - tickLength / 2, yTickPosition);
        context.lineTo(width / 2 + tickLength / 2, yTickPosition);
        context.stroke();
    }
}

const mainBtn = document.querySelector('button');
mainBtn.addEventListener('click', function(e) {
	e.preventDefault();
	console.log("button pressed!");
	const xVal = document.querySelector('input[name="x"]:checked').value;
	const yVal = document.querySelector('#yInp').value;
	const rVal = document.getElementById("r").value;
	let val = new validator();
	val.validate(xVal, yVal, rVal);
	if (val.getRespCode() == 0) {
		const url = new URL('controller', window.location.href);
		fetch(url.href, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				"x": xVal,
				"y": yVal,
				"r": rVal,
				"agent": navigator.userAgent
			})
		}).then(response => response.json()).then(data => {
			console.log("SUCCESS! ", data);
			const tbody = document.querySelector('#prevData');
			const newRow = document.createElement('tr');
			const tx = document.createElement('td');
			const ty = document.createElement('td');
			const tr = document.createElement('td');
			const tres = document.createElement('td');
			const textime = document.createElement('td');
			const tservtime = document.createElement('td');
			tx.textContent = xVal;
			ty.textContent = yVal;
			tr.textContent = rVal;
			tres.textContent = data.res;
			textime.textContent = data.exTime;
			let localDate = new Date(data.servTime);
			tservtime.textContent = localDate.toLocaleTimeString();
			newRow.appendChild(tx);
			newRow.appendChild(ty);
			newRow.appendChild(tr);
			newRow.appendChild(tres);
			newRow.appendChild(textime);
			newRow.appendChild(tservtime);
			tbody.appendChild(newRow);
		}).catch((error) => {
			console.error("ERROR! ", error);
		});
	}
});