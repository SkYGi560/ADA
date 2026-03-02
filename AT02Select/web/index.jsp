<%@page import="java.sql.Connection"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>DAM - Tabla de Datos - JSP y SQL</title>
</head>
<body>
<%
    //Estructura de datos para almacenar
    ArrayList<String> datos = new ArrayList<>();
    
    // Conexión a la BD
    String url;

    Class.forName("com.mysql.cj.jdbc.Driver"); 
    url = "jdbc:mysql://localhost:3306/bibliotecah?autoReconnect=true";
    url += "&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC"; 
    String usuario = "root"; 
    String password = "1234"; 
    Connection con = DriverManager.getConnection(url, usuario, password);
      
    // Crear Statement de la Consulta
    String sentenciaSQL = "SELECT id, titulo, autor FROM libros"; 
    Statement statement = con.createStatement(); 

    // Resulset
    ResultSet rs = statement.executeQuery(sentenciaSQL);
    while (rs.next()) { 
        datos.add(String.valueOf(rs.getInt(1))+"|"+rs.getString(2)+"|"+rs.getString(3));
    } 
    rs.close(); 

    // Cerrar conexión
	statement.close();
    con.close(); 
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