package bibliotecah;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.Scanner;

public class JPAVista {

    // ******************************************
    // verLibros usando NamedQuery
    // ******************************************
    private static List<Libros> findLibrosNamedQuery(LibrosJpaController dao) {
        EntityManager em = null;
        try {
            em = dao.getEntityManager();

            TypedQuery<Libros> tQuery
                    = em.createNamedQuery("Libros.findAll", Libros.class);

            return tQuery.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static List<Libros> findLibrosJQPL(LibrosJpaController dao) {
        EntityManager em = null;
        try {
            em = dao.getEntityManager();
            Query query = em.createQuery("SELECT lib FROM Libros lib ORDER BY lib.titulo");
            List<Libros> lista = query.getResultList();
            return lista;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void addLibro(LibrosJpaController dao) {
        System.out.println("===========================");
        System.out.println("Añadir un libro");
        System.out.println("===========================");
        String titulo, autor;
        // Java - Leer datos
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca el título: ");
        titulo = teclado.nextLine();
        System.out.print("Introduzca el autor: ");
        autor = teclado.nextLine();
        // Java - Cargar datos en un objeto Libro
        Libros libro = new Libros();
        libro.setId(0); // Para que obtenga el siguiente usando AUTO_INCREMENT
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        // JPA - Guardar datos del objeto directamente en la BD
        dao.create(libro);
    }

    public static void verLibrosNamedQuery(LibrosJpaController dao) {
        System.out.println();
        System.out.println("Mostrando todos los libros (NamedQuery):");

        //List<Libros> lista = findLibrosNamedQuery(dao);
        //List<Libros> lista = findLibrosJQPL(dao);
        List<Libros> lista = dao.findLibrosEntities();
        for (Libros libro : lista) {
            System.out.println(libro.getId() + ": " + libro.getTitulo()
                    + ", de " + libro.getAutor());
        }
    }

}
