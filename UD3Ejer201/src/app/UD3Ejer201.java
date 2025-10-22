/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.sql.*;

public class UD3Ejer201 {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url;
            url = "jdbc:mysql://localhost:3306/bdtareas";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String sentenciaSQL = "SELECT codigo, descripcion, fecha_prevista, terminada FROM tareas";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement(); 
                    ResultSet rs = statement.executeQuery(sentenciaSQL);
                    ) {
                System.out.println("+---------+------------------------------------------+------------------+-------------+");
                System.out.printf("| %-7s | %-40s | %-16s | %-11s |\n",
                        "Codigo", "Descripcion", "Fecha prevista", "Terminada");
                System.out.println("+---------+------------------------------------------+------------------+-------------+");

                while (rs.next()) {
                    System.out.printf("| %7s | %-40s | %-16s | %-11s |\n",
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getString("fecha_prevista"),
                            rs.getBoolean("terminada") ? "Si" : "No");
                }
                System.out.println("+---------+------------------------------------------+------------------+-------------+");
            } catch (SQLException se) {
                System.out.println(se.getErrorCode() + " " + se.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("MYSQL no accesible");
        }
    }
}
