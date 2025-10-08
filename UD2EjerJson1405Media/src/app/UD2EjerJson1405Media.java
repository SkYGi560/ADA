/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Alumno;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author alumno
 */
public class UD2EjerJson1405Media {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File archivo = new File("datos/alumnos.dat");

        try (BufferedReader br
                = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(archivo), StandardCharsets.UTF_8))) {

            String linea;
            var jArr = new JSONArray();
            while ((linea = br.readLine()) != null) {
                Float notaMedia = 0f;
                String[] partes = linea.split("\\|");
                var notas = new ArrayList<Float>();
                Integer contador = 3;
                while (contador < partes.length) {
                    notas.add(Float.valueOf(partes[contador]));
                    contador++;
                }
                Alumno alumno = new Alumno(Integer.valueOf(partes[0]), partes[1], partes[2], notas);
                var jObj = new JSONObject();
                jObj.put("id", alumno.getId());
                jObj.put("nombre", alumno.getNombre());
                jObj.put("localidad", alumno.getLocalidad());
                JSONArray jArrayNotas = new JSONArray();
                for (Float nota : alumno.getNotas()) {
                    jArrayNotas.put(nota);
                    notaMedia += nota;
                }
                notaMedia = notaMedia / alumno.getNotas().size();
                jObj.put("notas", jArrayNotas);
                jObj.put("numnotas", alumno.getNotas().size());
                jObj.put("notamedia", notaMedia);

                jArr.put(jObj);
            }
            System.out.println(jArr.toString(2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
