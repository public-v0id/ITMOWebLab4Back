export default class validator {
	constructor(respCode, msg) {
		this.respCode = respCode
		this.msg = msg
	}
	validate(xVal, yVal, rVal) {
		let xFlVal = parseFloat(xVal);
		let rFlVal = parseFloat(rVal);
		if ((isNaN(yVal)) || (yVal.trim() == "") || parseFloat(yVal) < -3 || parseFloat(yVal) > 5) {
			alert("Ошибка! Введено неправильное значение Y!");
			this.respCode = 1;
		}
		else if (![-5, -4, -3, -2, -1, 0, 1, 2, 3].includes(xFlVal)) {
			alert("Ошибка! Введено неправильное значение X!");
			this.respCode = 1;
		}
		else if (![1, 1.5, 2, 2.5, 3].includes(rFlVal)) {
			alert("Ошибка! Введено неправильное значение R!");
			this.respCode = 1;
		}
		else {
			this.respCode = 0;
		}
	}

	getRespCode() {
		return this.respCode;
	}

	getMessage() {
		return this.msg;
	}
}