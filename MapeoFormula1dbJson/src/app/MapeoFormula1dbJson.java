/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Equipo;
import clases.Piloto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class MapeoFormula1dbJson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        updateDB();
        ArrayList<Equipo> listaEquipos = leerArrayListFromDB();
        mostrarEquipos(listaEquipos);
        escribirGson(listaEquipos, "./datos/formula1.json");
    }

    public static void escribirGson(ArrayList<Equipo> listaEquipos, String fichero) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String salida = gson.toJson(listaEquipos);
        File archivo = new File(fichero);
        System.out.println(salida);
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

    public static void updateDB() {
        /*  DELETE FROM equipos WHERE id=7;
            INSERT INTO equipos (id,nomequipo, pais, anio_fundacion) VALUES (7,'Williams', 'Reino Unido', 1977);
            INSERT INTO pilotos (nompiloto, nacionalidad, edad, equipo_id); VALUES ('Alexander Albon', 'Tailandés', 27, 7);
            INSERT INTO pilotos (nompiloto, nacionalidad, edad, equipo_id) VALUES ('Nicholas Latifi', 'Canadiense', 28, 7); */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conexión a la BD
            String url;
            url = "jdbc:mysql://localhost:3306/formula1";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            // Crear Statement del CREATE TABLE

            try (Connection con = DriverManager.getConnection(url, usuario, password); Statement statement = con.createStatement();) {
                // Execute
                String sentenciaSQL = "";
                int cantidad = 0;

                sentenciaSQL = "DELETE FROM pilotos WHERE equipo_id=7;";
                cantidad += statement.executeUpdate(sentenciaSQL);

                sentenciaSQL = "DELETE FROM equipos WHERE id=7;";
                cantidad += statement.executeUpdate(sentenciaSQL);

                sentenciaSQL = "INSERT INTO equipos (id,nomequipo, pais, anio_fundacion)"
                        + " VALUES (7,'Williams', 'Reino Unido', 1977);";
                cantidad += statement.executeUpdate(sentenciaSQL);

                sentenciaSQL = "INSERT INTO pilotos (nompiloto, nacionalidad, edad, equipo_id) VALUES ('Alexander Albon', 'Tailandés', 27, 7);";
                cantidad += statement.executeUpdate(sentenciaSQL);

                sentenciaSQL = "INSERT INTO pilotos (nompiloto, nacionalidad, edad, equipo_id) VALUES ('Nicholas Latifi', 'Canadiense', 28, 7); ";
                cantidad += statement.executeUpdate(sentenciaSQL);

                if (cantidad == 0) {
                    System.out.println("Ningun registro actualizado");
                } else {
                    System.out.println("Actualizaciones realizadas");
                }

            } catch (SQLException ex) {
                System.out.println("Error en sentencia CREATE de SQL.");
                System.out.println(ex.getErrorCode() + " " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Equipo> leerArrayListFromDB() {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ArrayList<Piloto> pilotos = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conexión a la BD
            String url;
            url = "jdbc:mysql://localhost:3306/formula1";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String sentenciaSQL = "SELECT * FROM equipos";
            try (Connection con = DriverManager.getConnection(url, usuario, password); Statement statement = con.createStatement(); ResultSet rs = statement.executeQuery(sentenciaSQL);) {
                while (rs.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setNombre(rs.getString("nomequipo"));
                    equipo.setPais(rs.getString("pais"));
                    equipo.setFundacion(rs.getInt("anio_fundacion"));
                    String sentenciaSQL1 = "SELECT * FROM pilotos WHERE equipo_id='" + rs.getInt("id") + "'";
                    try (Statement statementPilotos = con.createStatement(); ResultSet rsPilotos = statementPilotos.executeQuery(sentenciaSQL1)) {
                        Piloto piloto = new Piloto();
                        while (rsPilotos.next()) {
                            piloto.setNombre(rsPilotos.getString("nompiloto"));
                            piloto.setNacionalidad(rsPilotos.getString("nacionalidad"));
                            piloto.setEdad(rsPilotos.getInt("edad"));
                            pilotos.add(piloto);
                        }
                    } catch (SQLException se) {
                        System.out.println(se.getMessage());
                    }
                    equipo.setPilotos(pilotos);
                    equipos.add(equipo);
                }
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        }

        return equipos;
    }

    public static void mostrarEquipos(ArrayList<Equipo> equipos) {
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
        System.out.println("| Nombre             | País         | Fundación | Pilotos | Piloto 1        | Piloto 2        |");
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
        for (var equipo : equipos) {
            System.out.printf("| %-18s | %-12s | %9d | %7d | %-15s | %-15s |\n",
                    equipo.getNombre(),
                    equipo.getPais(),
                    equipo.getFundacion(),
                    equipo.getPilotos().size(),
                    equipo.getPilotos().get(0).getNombre(),
                    equipo.getPilotos().get(1).getNombre());
        }
        System.out.println("+--------------------+--------------+-----------+---------+-----------------+-----------------+");
    }
}
