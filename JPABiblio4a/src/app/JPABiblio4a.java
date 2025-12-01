/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import bibliotecah3.Autores;
import bibliotecah3.AutoresJpaController;
import bibliotecah3.Libros;
import bibliotecah3.LibrosJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class JPABiblio4a {

    public static void verLibrosFind(LibrosJpaController dao) {
        System.out.println("Mostrando todos los libros y sus autores:");
        System.out.println("========================================");
        List<Libros> lista = dao.findLibrosEntities();
        for (Libros libro : lista) {
            System.out.println(libro.getTitulo()
                    + " (" + libro.getId() + "). Autores: ");
            for (Autores autor : libro.getAutoresCollection()) {
                System.out.println(" => " + autor.getNombre());
            }
        }
    }

    public static void verAutoresFind(AutoresJpaController dao) {
        System.out.println("Mostrando todos los autores y sus libros:");
        System.out.println("========================================");
        List<Autores> lista = dao.findAutoresEntities();
        for (Autores autor : lista) {
            System.out.println(autor.getNombre()
                    + " (" + autor.getCod() + "). Libros: ");
            for (Libros libro : autor.getLibrosCollection()) {
                System.out.println(" => " + libro.getTitulo());
            }
        }
    }

    public static void main(String[] args) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("JPABiblio4PU");
        LibrosJpaController daoLibros = new LibrosJpaController(emf);
        AutoresJpaController daoAutores = new AutoresJpaController(emf);
        verLibrosFind(daoLibros);
        verAutoresFind(daoAutores);
        emf.close();
    }

}
