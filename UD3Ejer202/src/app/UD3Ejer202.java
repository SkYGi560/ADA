/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.sql.*;

/**
 *
 * @author joel
 */
public class UD3Ejer202 {

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
            String sentenciaSQL = "CREATE TABLE IF NOT EXISTS ejecuciones("
                    + "	id INT AUTO_INCREMENT PRIMARY KEY,"
                    + " codprograma INT,"
                    + " fecha DATE,"
                    + " usuario VARCHAR(100))";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement();) {
                
                statement.executeUpdate(sentenciaSQL);
                System.out.println("Tabla ejecuciones creada");
            } catch (SQLException ex) {
                System.out.println("Error en sentencia CREATE de sql");
                System.out.println(ex.getErrorCode() + " " + ex.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("MYSQL no accesible");
        }
    }
}
