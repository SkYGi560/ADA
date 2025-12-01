package bdcloud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class JPAVista {

    public static void verInfraestructuraPorTipo(InfraestructuraJpaController dao) {
        Scanner sc = new Scanner(System.in);
        Float total = 0f;

        System.out.println();
        System.out.println("Mostrando infraestructura de un tipo:");
        System.out.println("=====================================");

        System.out.print("Introduzca el tipo: ");
        String tipo = sc.nextLine();

        List<Infraestructura> lista = null;
        try (EntityManager em = dao.getEntityManager()) {
            TypedQuery<Infraestructura> query = em.createQuery("SELECT i FROM Infraestructura i WHERE i.tipo = :tipo", Infraestructura.class);
            query.setParameter("tipo", tipo);
            lista = query.getResultList();
        }
        System.out.println("+----+---------------------------+-----+----+--------+------------+");
        System.out.println("| ID | DESCRIPCIÓN               | PLT | TP | PRECIO | CREADA     |");
        System.out.println("+----+---------------------------+-----+----+--------+------------+");
        for (var infraestructura : lista) {
            System.out.printf("| %-2s | %-25s | %3s | %2s | %6f€ | %10s |\n",
                    infraestructura.getIdService(),
                    infraestructura.getDesService(),
                    infraestructura.getPlataforma().getIdPform(),
                    infraestructura.getTipo(),
                    infraestructura.getPrecioMes(),
                    new SimpleDateFormat("dd/MM/yyyy").format(infraestructura.getCreada()));
            total += infraestructura.getPrecioMes().floatValue();
        }
        System.out.println("+----+---------------------------+-----+----+--------+------------+");
        System.out.printf("| %-2s | %-25s | %3s | %3s | %6f€ | %10s |\n",
                "",
                "",
                "",
                "",
                total,
                "");
        System.out.println("+----+---------------------------+-----+----+--------+------------+");
    }

    public static void modificarInfraestructura(InfraestructuraJpaController daoInfraestructura) {
        System.out.println("===========================");
        System.out.println("Modificar una Infraestructura");
        System.out.println("===========================");
        Integer codInfra;
        String idPlataformaNueva;
        Double precio;
        Infraestructura infraestructuraModificada = null;
        Scanner teclado = new Scanner(System.in);

        while (infraestructuraModificada == null) {
            System.out.print("Introduzca el codigo de la Infraestructura: ");
            codInfra = Integer.valueOf(teclado.nextLine());
            infraestructuraModificada = daoInfraestructura.findInfraestructura(codInfra);
            if (infraestructuraModificada != null) {
                System.out.println(infraestructuraModificada.getDesService() + " (" + infraestructuraModificada.getPlataforma().getIdPform() + " - " + infraestructuraModificada.getPrecioMes() + "€)");
            } else {
                System.out.println("La plataforma " + codInfra + " no existe");
            }
        }
        System.out.print("Introduzca el ID de la nueva PLATAFORMA: ");
        idPlataformaNueva = teclado.nextLine();
        System.out.println("Introduzca el nuevo PRECIO mensual: ");
        precio = Double.valueOf(teclado.nextLine());

        Plataforma plataforma = new Plataforma();
        plataforma.setIdPform(idPlataformaNueva);
        plataforma.setNomPform(infraestructuraModificada.getPlataforma().getNomPform());
        plataforma.setWebPform(infraestructuraModificada.getPlataforma().getWebPform());
        plataforma.setInfraestructuraCollection(infraestructuraModificada.getPlataforma().getInfraestructuraCollection());
        infraestructuraModificada.setPlataforma(plataforma);
        infraestructuraModificada.setPrecioMes(precio);
        try {
            daoInfraestructura.edit(infraestructuraModificada);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
