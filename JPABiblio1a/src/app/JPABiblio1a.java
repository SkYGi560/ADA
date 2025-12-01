/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

import bibliotecah.Libros;
import bibliotecah.LibrosJpaController;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author alumno
 */
public class JPABiblio1a {

    public static void verLibrosFind(LibrosJpaController dao) {
        System.out.println("Mostrando todos los libros:");
        List<Libros> lista = dao.findLibrosEntities();
        for (Libros libro : lista) {
            System.out.println(libro.getId() + ": " + libro.getTitulo()
                    + ", de " + libro.getAutor());
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        LibrosJpaController dao = new LibrosJpaController(emf);
        verLibrosFind(dao);

        emf.close();
    }

}
