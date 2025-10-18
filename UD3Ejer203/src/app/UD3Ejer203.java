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
public class UD3Ejer203 {

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
            String sentenciaSQL = "ALTER TABLE programas"
                    + " ADD COLUMN ocupacion INT DEFAULT 0";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement();) {
                
                statement.executeUpdate(sentenciaSQL);
                System.out.println("Columna ocupacion creada");
            } catch (SQLException ex) {
                System.out.println("Error en sentencia ALTER TABLE de sql");
                System.out.println(ex.getErrorCode() + " " + ex.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("MYSQL no accesible");
        }
    }
    
}
