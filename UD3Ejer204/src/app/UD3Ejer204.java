/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author joel
 */
public class UD3Ejer204 {

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
            String sentenciaSQL1 = "UPDATE programas"
                    + "	SET ocupacion = 10"
                    + " WHERE codigo IN(1,2);";
            String sentenciaSQL2 = "INSERT INTO ejecuciones(codprograma,fecha,usuario)"
                    + "VALUES"
                    + "(1,'2025-10-18','pepe'),"
                    + "(2,'2025-10.18','pepe');";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement();) {

                statement.executeUpdate(sentenciaSQL1);
                statement.executeUpdate(sentenciaSQL2);
                System.out.println("Ocupaciones creadas y INSERTS añadidos a la tabla ejecuciones");
            } catch (SQLException ex) {
                System.out.println("Error en sentencia INSERT o UPDATE de sql");
                System.out.println(ex.getErrorCode() + " " + ex.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("MYSQL no accesible");
        }
    }

}
