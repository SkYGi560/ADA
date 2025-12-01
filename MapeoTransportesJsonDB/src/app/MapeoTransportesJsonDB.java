/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class MapeoTransportesJsonDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Conductor> listaConductores = leerArrayListFromBD();
        mostrarConductores(listaConductores);
        escribirJson(listaConductores, "./datos/conductor.json");
    }

    public static void escribirJson(List<Conductor> listaConductores, String ficheroSalida) {
        Scanner sc = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File archivo = new File(ficheroSalida);
        String salida = "";
        
        System.out.println("Dime la matricula que quieres que salga en el fichero: ");
        String matricula = sc.nextLine();
        for(var conductor : listaConductores){
            if(conductor.getVehiculo().getMatricula().equals(matricula)){
                salida = gson.toJson(conductor);
            }
        }
        
       // Apertura del fichero y creación de PrintWriter
        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(archivo, false), StandardCharsets.UTF_8)))) {
            pw.println(salida);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
    }

    public static List<Conductor> leerArrayListFromBD() {
        List<Conductor> conductores = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conexión a la BD
            String url;
            url = "jdbc:mysql://localhost:3306/bdtransportistas";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String sentenciaVehiculos = "SELECT * FROM vehiculos";
            try (   Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement(); 
                    ResultSet rsVehiculos = statement.executeQuery(sentenciaVehiculos);) {
                List<Vehiculo> vehiculos = new ArrayList<>();
                while (rsVehiculos.next()) {
                    Vehiculo vehiculo = new Vehiculo(
                            rsVehiculos.getInt("id"),
                            rsVehiculos.getString("tipo"),
                            rsVehiculos.getFloat("capacidad_carga"),
                            rsVehiculos.getString("matricula"));
                    vehiculos.add(vehiculo);
                }
                String sentenciaConductores = "SELECT * FROM transportistas";
                ResultSet rsTransportistas = statement.executeQuery(sentenciaConductores);
                while(rsTransportistas.next()){
                    Vehiculo vehiculo = new Vehiculo();
                    for(var v : vehiculos){
                        if(v.getId().equals(rsTransportistas.getInt("id_vehiculo"))){
                            vehiculo = v;
                        }
                    }
                    Conductor conductor = new Conductor(
                            rsTransportistas.getInt("id_transportista"),
                            rsTransportistas.getString("nombre"),
                            rsTransportistas.getString("tipo_licencia"),
                            rsTransportistas.getInt("experiencia"),
                            vehiculo
                    );
                    conductores.add(conductor);
                }
            } catch (SQLException se) {
                System.out.println("Hubo un error con el SELECT ");
                System.out.println(se.getErrorCode() + " " + se.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println("Hubo un error al acceder a la base de datos");
        }
        return conductores;
    }

    
    //Cambiar la salida por un int en vez de un float
    public static void mostrarConductores(List<Conductor> listaConductores) {
        System.out.println("+----------------------+-----+-------------+------------+----------------------+------------+");
        System.out.println("| Conductor            | Lic | Experiencia | Matrícula  | Tipo                 | Carga (Kg) |");
        System.out.println("+----------------------+-----+-------------+------------+----------------------+------------+");

        for (Conductor conductor : listaConductores) {
            
            System.out.printf("| %-20s | %-3s | %11d | %-10s | %-20s | %10d |\n",
                    conductor.getTransportista(),
                    conductor.getTipoLicencia(),
                    conductor.getExperiencia(),
                    conductor.getVehiculo().getMatricula(),
                    conductor.getVehiculo().getTipo(),
                    conductor.getVehiculo().getCapacidadCarga().intValue());
        }
        System.out.println("+----------------------+-----+-------------+------------+----------------------+------------+");
    }

}
