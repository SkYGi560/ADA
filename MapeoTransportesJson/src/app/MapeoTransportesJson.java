/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import java.util.ArrayList;
import org.json.JSONObject;
import utils.JSONUtils;

/**
 *
 * @author alumno
 */
public class MapeoTransportesJson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Conductor> listaConductores
                = leerFicheroJson("./datos/transportistas.json");
        mostrarConductores(listaConductores);
    }

    public static ArrayList<Conductor> leerFicheroJson(String archivo) {
        JSONObject jObjVehiculo;
        JSONObject jObjConductor;
        ArrayList<Conductor> lista = new ArrayList<>();

        for (var conductor : JSONUtils.getArrayFromFile(archivo)) {
            Vehiculo vehiculo = new Vehiculo();
            Conductor conductorFinal = new Conductor();
            jObjConductor = (JSONObject) conductor;
            conductorFinal.setTransportista(jObjConductor.getString("transportista"));
            conductorFinal.setTipoLicencia(jObjConductor.getString("tipoLicencia"));
            conductorFinal.setExperiencia(jObjConductor.getInt("experiencia"));

            jObjVehiculo = jObjConductor.getJSONObject("vehiculo");
            vehiculo.setTipo(jObjVehiculo.getString("tipo"));
            vehiculo.setCapacidadCarga(jObjVehiculo.getInt("capacidadCarga"));
            vehiculo.setMatricula(jObjVehiculo.getString("matricula"));

            conductorFinal.setVehiculo(vehiculo);
            lista.add(conductorFinal);
        }
        return lista;
    }

    public static void mostrarConductores(ArrayList<Conductor> listaConductores) {
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
                    conductor.getVehiculo().getCapacidadCarga());
        }
        System.out.println("+----------------------+-----+-------------+------------+----------------------+------------+");
    }
}
