"use strict";

const graph = document.getElementById("graph");
const context = graph.getContext("2d");
const margin = 15;

window.onload = function () {
    updateGraph();
}

function updateGraph() {
    console.log("Updating");
    drawGraph(3);

    let savedData = loadData();
    let savedLen = savedData.length;
    console.log("Length " + savedLen);
    for (let i = 0; i < savedLen; ++i) {
        let rVal = savedData[i].r;
        let rCur = document.getElementById("form:rInput").value;
        if (rVal == rCur) {
            let xVal = savedData[i].x;
            let yVal = savedData[i].y;
            let res = savedData[i].res;
            console.log(xVal + " " + yVal + " " + rVal + " " + res)
            drawPoint(xVal, yVal, rVal, res);
        }
    }
}

const saveData = (points) => {
	window.sessionStorage.setItem('Points', JSON.stringify(points));
};

const loadData = () => {
	const savedData = window.sessionStorage.getItem('Points');
	return savedData ? JSON.parse(savedData) : [];
};

let data = loadData();

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

graph.addEventListener('click', function() {
    document.getElementById("form:hit").value = "unknown";
    let clickX = event.clientX - graph.getBoundingClientRect().left;
    let clickY = event.clientY - graph.getBoundingClientRect().top;
    let xSize = graph.width;
    let ySize = graph.height;
    console.log("clicked! On " + clickX + " " + clickY);
    console.log("X " + graph.getBoundingClientRect().left + " Y " + graph.getBoundingClientRect().top);
    console.log("PageX " + event.pageX + " PageY " + event.pageY);
    const rVal = document.getElementById("form:rInput").value;
    const xVal = (Math.round((clickX-xSize/2)*parseFloat(rVal)*3000000/xSize)/1000000.0);
    const yVal = (Math.round((ySize/2-clickY)*parseFloat(rVal)*3000000/ySize)/1000000.0);
    console.log(xVal, yVal, rVal);
    document.getElementById("form:xInput_input").value = xVal;
    document.getElementById("form:yInput").value = yVal;
    PrimeFaces.ab({
            source: "form:btn",
            update: "form:btn form:resTable form:hit",
                            oncomplete: function() {
                                    console.log("OnComplete");
                                    if (document.getElementById("form:hit").value != "unknown") {
                                            drawPoint(xVal, yVal, rVal, document.getElementById("form:hit").value == "true");
                                            data.push({ x: xVal, y: yVal, r: rVal, res: document.getElementById("form:hit").value == "true"});
                                            saveData(data);
                                            console.log(data.length)
                                        }
                                }
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
    context.lineTo(width / 6, height / 2);
    context.lineTo(width / 2, 2*height / 3);
    context.closePath();
    context.fill();
    context.strokeStyle = "#0000FF";
    context.stroke();
    context.fillStyle = "#FFFF0010"; // yellow with 10% opacity
    context.fillRect(width / 2, height / 2, width/3, height/3);
    context.strokeStyle = "#FFFF00";
    context.fillRect(width / 2, height / 2, width/3, height/3);
    context.fillStyle = "#39FF1410"; // green with 10% opacity
    context.beginPath();
    context.arc(width / 2, height / 2, height/6, -0.5*Math.PI, 0);
    context.lineTo(width / 2, height / 2);
    context.closePath();
    context.fill();
    context.strokeStyle = "#39FF14";
    context.beginPath();
    context.arc(width / 2, height / 2, height/6, -0.5*Math.PI, 0);
    context.stroke();
    context.fillStyle = "white";
    const labelR = "R";
    const labelRHalf = "R/2";
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

		function handleResponse(xhr, status, args) {
			console.log("xhr: " + xhr);
			console.log("status: " + status);
			console.log("args: " + args);
		}