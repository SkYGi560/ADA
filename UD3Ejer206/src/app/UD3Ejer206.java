/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Programa;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author joel
 */
public class UD3Ejer206 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {            
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Conexión a la BD
            String url;
            url = "jdbc:mysql://localhost:3306/bdapps";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String leeBD = "SELECT * FROM programas";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement(); 
                    ResultSet rs = statement.executeQuery(leeBD);) {
                ArrayList<Programa> programas = new ArrayList<>();
                while (rs.next()) {
                    var programa = new Programa(
                            rs.getInt("codigo"),
                            rs.getString("nombre"),
                            rs.getString("carpeta_instalacion"),
                            rs.getInt("ocupacion"));
                    programas.add(programa);
                }
                Boolean existeCodigo;
                Integer codigo;
                System.out.println("Dime el codigo del programa que quieres dar de alta en la base de datos: ");
                do {
                    existeCodigo = false;
                    codigo = sc.nextInt();
                    for (var programa : programas) {
                        if (programa.getCodigo() == codigo) {
                            existeCodigo = true;
                            System.out.println("El codigo ya existe, prueba con otro codigo: ");
                        }
                    }
                } while (existeCodigo);
                sc.nextLine();
                System.out.println("Dime el nombre: ");
                String nombre = sc.nextLine();
                System.out.println("Dime la carpeta de instalacion: ");
                String carpeta_instalacion = sc.nextLine();
                System.out.println("Dime la ocupacion que tiene este archivo: ");
                Integer ocupacion = sc.nextInt();
                String sentenciaSQL = "INSERT INTO programas(nombre,carpeta_instalacion,ocupacion) "
                        + "VALUES "
                        + "('"+nombre+"', '"+carpeta_instalacion+"', "+ocupacion+")";
                System.out.println("Se ha creado la entrada con exito");
                statement.executeUpdate(sentenciaSQL);
            } catch (SQLException ex) {
                System.out.println("Error en sentencia INSERT de sql");
                System.out.println(ex.getErrorCode() + " " + ex.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        }
    }
}
