/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Programa;
import java.sql.*;
import java.util.ArrayList;
import com.google.gson.*;

/**
 *
 * @author joel
 */
public class UD3Ejer205 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conexión a la BD
            String url;
            url = "jdbc:mysql://localhost:3306/bdapps";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String sentenciaSQL = "SELECT * FROM programas";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement(); 
                    ResultSet rs = statement.executeQuery(sentenciaSQL);) {
                ArrayList<Programa> programas = new ArrayList<Programa>();
                while (rs.next()) {
                    Programa programa = new Programa(
                            rs.getInt("codigo"),
                            rs.getString("nombre"),
                            rs.getString("carpeta_instalacion"),
                            rs.getInt("ocupacion"));
                    programas.add(programa);
                }
                for(var programa : programas){
                    System.out.println(programa.toJson());
                }

            } catch (SQLException se) {
                System.out.println("Hubo un error con el SELECT ");
                System.out.println(se.getErrorCode() + " " + se.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("Hubo un error al acceder a la base de datos");
        }
    }

}
