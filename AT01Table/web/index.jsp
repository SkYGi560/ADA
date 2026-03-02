<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>DAM - Tabla de Datos - JSP</title>
</head>
<body>
<%
  ArrayList<String> datos = new ArrayList<>();
        
  datos.add("1|Macbeth|William Shakespeare");
  datos.add("2|La Celestina (Tragicomedia de Calisto y Melibea)|Fernando de Rojas");
  datos.add("3|El Lazarillo de Tormes|Anónimo");
  datos.add("4|20.000 Leguas de Viaje Submarino|Julio Verne");
  datos.add("5|Alicia en el País de las Maravillas|Lewis Carrol");
  datos.add("6|Cien Años de Soledad|Gabriel García Márquez");
  datos.add("7|La tempestad|William Shakespeare");
  datos.add("8|Pruebas|Joel Vives");  
%>
	<div>
		<p>Número de registros: <%= datos.size() %></p>
	</div>

<% 	if (datos.size()>0) { %>	
	<table border="1" style="border-collapse:collapse">
	  <thead style="background-color:orange">
		<tr>
			<td>ID LIBRO</td>
			<td>TÍTULO</td>
			<td>AUTOR</td>
		</tr>
      </thead>		
	  <tbody>
<%
		for (int i=0; i<datos.size(); i++) {		
			String[] registro = datos.get(i).split(Pattern.quote("|"));
%>
				<tr>
                                    <td><% out.println(registro[0]); %></td>
                                    <td><% out.println(registro[1]); %></td>
                                    <td><% out.println(registro[2]); %></td>
				</tr>
<%
		}
%>
	  </tbody>
	</table>
<%		
		} else {
%>
	<div>
		<p>No hay datos que mostrar</p>
	</div>

<%
        }
%>
</body>
</html>