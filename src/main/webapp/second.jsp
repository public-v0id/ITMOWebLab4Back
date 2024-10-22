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
				<td>
					<h1 class = "header">Игнатов Петр Дмитриевич, P3216, вариант 408864</h1>
				</td>
			</tr>
			<tr class="inp">
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
						        int i = resList.size() - 1;
						        out.println("<tr>");
						        for (int j = 0; j < resList.get(i).size()-1; ++j) {
						            out.println("<td>"+resList.get(i).get(j)+"</td>");
						        }
						        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.parse(resList.get(i).get(resList.get(i).size()-1)), ZoneId.systemDefault());
						        out.println("<td>"+ldt.format(outputFormatter)+"</td>");
						        out.println("</tr>");
						    }
						%>
					</table>
				</td>
			</tr>
			<tr>
			    <td>
			        <a href="controller">Ссылка на форму</a>
			    </td>
			</tr>
			<tr>
				<td class = "header">
					<h6>г. Санкт-Петербург, НИУ "ИТМО", Факультет Программной Инженерии и Компьютерных Технологий, 2024 г.</h6>
				</td>
			</tr>
		</table>
	</body>
</html>