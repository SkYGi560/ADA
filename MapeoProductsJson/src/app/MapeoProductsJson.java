/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import clases.Dimensiones;
import clases.Product;
import java.util.ArrayList;
import utils.JSONUtils;
import org.json.*;

/**
 *
 * @author alumno
 */
public class MapeoProductsJson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Product> listaProduct = leerFicheroJson("./datos/shop-products.json");
        mostrarEquipos(listaProduct);
    }

    public static ArrayList<Product> leerFicheroJson(String archivo) {
        JSONObject dimen;
        JSONObject prod;
        ArrayList<Product> lista = new ArrayList<>();

        for (var producto : JSONUtils.getArrayFromFile(archivo)) {
            Dimensiones dimensiones = new Dimensiones();
            Product productoFinal = new Product();
            ArrayList<String> colores = new ArrayList();
            prod = (JSONObject) producto;
            productoFinal.setArt(prod.getString("art"));
            productoFinal.setCategoria(prod.getString("categoria"));
            productoFinal.setDescrip(prod.getString("descrip"));
            productoFinal.setPrecio(prod.getDouble("precio"));

            dimen = prod.getJSONObject("dimensiones");
            dimensiones.setLargo(dimen.getInt("largo"));
            dimensiones.setAncho(dimen.getInt("ancho"));
            dimensiones.setEspesor(dimen.getInt("espesor"));

            productoFinal.setDimensiones(dimensiones);
            for (var color : prod.getJSONArray("colores")) {
                colores.add((String) color);
            }
            productoFinal.setColores(colores);
            lista.add(productoFinal);
        }
        return lista;
    }

    public static void mostrarEquipos(ArrayList<Product> listaProductos) {
        System.out.println("+-----+----------------------+-----+--------+-------------+----------------------+");
        System.out.println("| Art | Descripción          | Cat | Precio | Dimensiones | Color                |");
        System.out.println("+-----+----------------------+-----+--------+-------------+----------------------+");

        for (Product producto : listaProductos) {
            System.out.printf("| %3s | %20s | %3s | %6s | %11s | %-20s |\n",
                    producto.getArt(),
                    producto.getDescrip(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getDimensiones().toString(),
                    String.join(",", producto.getColores()));
        }

        System.out.println("+-----+----------------------+-----+--------+-------------+----------------------+");
    }
}


