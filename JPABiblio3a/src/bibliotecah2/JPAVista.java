/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bibliotecah2;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class JPAVista {

    public static void verLibrosFind(LibrosJpaController dao) {
        System.out.println("Mostrando todos los libros:");
        System.out.println("===========================");
        List<Libros> lista = dao.findLibrosEntities();
        for (Libros libro : lista) {
            System.out.println(libro.getId() + ": " + libro.getTitulo()
                    + ", de " + libro.getCodautor().getNombre());
        }
    }

    public static void verAutoresFind(AutoresJpaController dao) {
        System.out.println("Mostrando todos los libros:");
        System.out.println("===========================");
        List<Autores> lista = dao.findAutoresEntities();
        for (Autores autor : lista) {
            System.out.println(autor.getCod() + ": " + autor.getNombre());
        }
    }

    public static void addLibros(AutoresJpaController daoAutores, LibrosJpaController daoLibros) {
        Integer idlibro = 0;
        String titulo;
        String codAutor;
        Autores autor;
        System.out.println("===========================");
        System.out.println("Añadir un Libro");
        System.out.println("===========================");
        // Java - Leer datos
        Scanner teclado = new Scanner(System.in);

        System.out.print("Introduzca el titulo: ");
        titulo = teclado.nextLine();

        System.out.print("Introduzca el código del autor: ");
        codAutor = teclado.nextLine();
        // Java - Cargar datos en un objeto
        Libros libro = new Libros();
        libro.setId(idlibro);
        libro.setTitulo(titulo);
        autor = daoAutores.findAutores(codAutor);

        if (autor != null) {
            libro.setCodautor(autor);
            daoLibros.create(libro);
        } else {
            System.out.println("El Autor " + codAutor + " no existe");
        }
    }
}
