
<%@page import="com.dam.persona.Domicilio"%>
<%@page import="com.dam.persona.Persona"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            table {
                border-collapse: collapse;
            }
            table, td {
                border: 1px solid black;
            }
            .cabecera {
                background-color: orange;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <h1>Persona</h1>
        <p><%
            Persona persona = new Persona(21444555, "Sergio", "Fernández", "61277788",
                    new Domicilio("C/Jaume de Scals, 35", "03100", "Xixona", "Alicante"));
            out.println(persona.toString());
            %></p>
        <table>
            <tr>
                <td class="cabecera">DNI</td>
                <td><%= persona.getDni()%></td>
            </tr>
            <tr>
                <td class="cabecera">NOMBRE</td>
                <td><%= persona.getNombre()%></td>
            </tr>
            <tr>
                <td class="cabecera">APELLIDOS</td>
                <td><%= persona.getApellidos()%></td>
            </tr>
            <tr>
                <td class="cabecera">TELÉFONO</td>
                <td><%= persona.getTelefono()%></td>
            </tr>
            <tr>
                <td class="cabecera">DIRECCIÓN</td>
                <td><%= persona.getDomicilio().getDireccion()%></td>
            </tr>
            <tr>
                <td class="cabecera">CÓDIGO POSTAL</td>
                <td><%= persona.getDomicilio().getCpostal()%></td>
            </tr>
            <tr>
                <td class="cabecera">POBLACIÓN</td>
                <td><%= persona.getDomicilio().getPoblacion()%></td>
            </tr>
            <tr>
                <td class="cabecera">PROVINCIA</td>
                <td><%= persona.getDomicilio().getProvincia()%></td>
            </tr>
        </table>
    </body>
</html>