<%@ page import="java.util.List" %>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.*" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Лабораторная работа 2 Игнатов П.</title>
		<style>
			html, body {
				height: 100%
			}
			body{
				background-image: url('http://grob-hroniki.org/img/other/original/1989_rpe/rpe_01.jpg');
				background-repeat: no-repeat;
				background-attachment: fixed;
				background-position: center;
				background-size: cover;
				font-size: 16px;
			}
			table {
				width: 100%;
				border: 1%;
				height: 100%;
			}
			tr {
				background-color: #FFFFFF00;
			}
			.inp {
				background-color: #C0C0C088;
			}
			td {
				padding: 1%;
			}
			.expandable {
				width: auto;
			}
			#btn {
				width: 75%;
				height: 75%;
			}
			.center {
				display:block;
				margin: 0 auto;
			}
			#prevData {
				height: 60%;
			}
			.prevDataText {
				font-size: 10px;
			}
			.header {
				text-align: center;
			}
		</style>
	</head>
	<body>
		<table>
			<tr>
				<td colspan="3">
					<h1 class = "header">Игнатов Петр Дмитриевич, P3216, вариант 408864</h1>
				</td>
			</tr>
			<tr class="inp">
				<td class="expandable">
                	<form>
                		<h3 class = "header">X:</h3>
                		<p>
                    		<div class = "header">
                    			<input type="radio" name="x" id="-5x" value="-5" checked="checked"> </input>
                    			<label for="-5x">-5</label>
                    			<input type="radio" name="x" id="-4y" value="-4"></input>
                			    <label for="-4x">-4</label>
                		    	<input type="radio" name="x" id="-3x" value="-3"></input>
                	    		<label for="-3x">-3</label>
                    			<input type="radio" name="x" id="-2x" value="-2"></input>
                    			<label for="-2x">-2</label>
                    			<input type="radio" name="x" id="-1x" value="-1"></input>
                    			<label for="-1x">-1</label>
                			    <input type="radio" name="x" id="0x" value="0"></input>
                		    	<label for="0x">0</label>
                	        	<input type="radio" name="x" id="1x" value="1"></input>
                    			<label for="1x">1</label>
   			    				<input type="radio" name="x" id="2x" value="2"></input>
   		    					<label for="2x">2</label>
                    			<input type="radio" name="x" id="3x" value="3"></input>
                    			<label for="3x">3</label>
                		    </div>
            			</p>
            		</form>
            	</td>
				<td class="expandable">
                	<form>
                		<h3 class = "header">Y:</h3>
                		<p>
                			<div class = "header">
                			<input type="text" id="yInp" name="Y:"></input>
                		</p>
                	</form>
                </td>
				<td class="expandable">
					<div class = "header">
                        <canvas id="graph" width="300" height="300"></canvas>
					</div>
				</td>
			</tr>
			<tr class="inp">
				<td class="expandable">
            		<button value="check" class = "center" id = "btn">Проверить</button>
            	</td>
			    <td class="expandable">
                	<form method="get">
                		<h3 class = "header">R:</h3>
                		<p>
                		<div class = "header">
                			<select id="r">
                    			<option value="1">1</option>
                				<option value="1.5">1.5</option>
                	    		<option value="2">2</option>
                				<option value="2.5">2.5</option>
                				<option value="3">3</option>
                			</select>
                		</div>
                		</p>
                	</form>
                </td>
				<td class="expandable">
					<h3 class = "header">Предыдущие данные</h3>
					<table id="prevData" border=1>
						<tr>
							<th><h5>X</h5></th>
							<th><h5>Y</h5></th>
							<th><h5>R</h5></th>
							<th><h5>Ответ</h5></th>
							<th><h5>Время выполнения</h5></th>
							<th><h5>Время</h5></th>
						</tr>
						<%
						    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
						    List<List<String>> resList = (List<List<String>>)application.getAttribute("resList");
						    if (resList != null) {
						        for (int i = 0; i < resList.size(); ++i) {
						            out.println("<tr>");
						                for (int j = 0; j < resList.get(i).size()-1; ++j) {
						                    out.println("<td>"+resList.get(i).get(j)+"</td>");
						                }
						                LocalDateTime ldt = LocalDateTime.ofInstant(Instant.parse(resList.get(i).get(resList.get(i).size()-1)), ZoneId.systemDefault());
						                out.println("<td>"+ldt.format(outputFormatter)+"</td>");
						            out.println("</tr>");
						        }
						    }
						%>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="3" class = "header">
					<h6>г. Санкт-Петербург, НИУ "ИТМО", Факультет Программной Инженерии и Компьютерных Технологий, 2024 г.</h6>
				</td>
			</tr>
		</table>
		<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
		<script type="module" src="main.js"></script>
	</body>
</html>