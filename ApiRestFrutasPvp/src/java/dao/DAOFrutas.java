package dao;

import clases.Frutas;
import clases.ListaFrutas;

public class DAOFrutas {

    private static final ListaFrutas frutas = new ListaFrutas();

    static {
        frutas.add(new Frutas("platano", 10, 1.45));
        frutas.add(new Frutas("pera", 15, 2.19));
        frutas.add(new Frutas("manzana", 20, 1.95));
    }

    // Método para recuperar todos los datos
    public static ListaFrutas getAll() {
        return frutas;
    }

    // Método para recuperar un dato determinado
    public static Frutas get(String nombre) {
        Frutas obj = null;
        for (Frutas fruta : frutas.getLista()) {
            if (fruta.getNombre().equals(nombre)) {
                obj = fruta;
            }
        }
        return obj;
    }

    // Método para recuperar un dato determinado
    public static boolean existe(String nombre) {
        boolean resul = false;
        for (Frutas fruta : frutas.getLista()) {
            if (fruta.getNombre().equals(nombre)) {
                resul = true;
            }
        }
        return resul;
    }

    // Método para añadir un dato a la base de datos
    public static boolean add(Frutas fruit) {
        boolean resul;
        boolean encontrado = false;
        for (Frutas fruta : frutas.getLista()) {
            if (fruta.getNombre().equals(fruit.getNombre())) {
                encontrado = true;
            }
        }
        if (!encontrado) {
            frutas.add(fruit);
            resul = true;
        } else {
            resul = false;
        }
        return resul;

    }

    // Método para actualizar Kg
    public static boolean put(Frutas fruit) {
        boolean resul = false;
        for (Frutas fruta : frutas.getLista()) {
            if (fruta.getNombre().equals(fruit.getNombre())) {
                if (fruta.getKg() != fruit.getKg()) {
                    fruta.setKg(fruit.getKg());
                    resul = true;
                }
                if (fruta.getPrecio() != fruit.getPrecio()) {
                    fruta.setPrecio(fruit.getPrecio());
                    resul = true;
                }
            }
        }
        return resul;
    }

    // Método para eliminar un dato de la base de datos
    public static boolean delete(String nombre) {
        boolean resul = false;
        for (Frutas fruta : frutas.getLista()) {
            if (fruta.getNombre().equals(nombre)) {
                System.out.println("Eliminando: " + fruta.getNombre());
                frutas.remove(fruta);
                resul = true;
                break; // Salir del for
            }
        }
        return resul;
    }
}
