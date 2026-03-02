package dao;

import clases.Libros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOLibros {

    static Connection con = null;


    /* ------------------- */
    /* CONECTAR */
    /* ------------------- */
    public static void conectar() {
        try {
            // Conexión a la BD
            if (!IsConectado()) {
                String url;

                Class.forName("com.mysql.cj.jdbc.Driver");
                url = "jdbc:mysql://localhost:3306/biblioteca";
                url += "?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&serverTimezone=UTC";

                String usuario = "root";
                String password = "1234";
                con = DriverManager.getConnection(url, usuario, password);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* ------------------- */
    /* DESCONECTAR */
    /* ------------------- */
    public static void desconectar() {
        try {
            // Cerrar conexión
            if (IsConectado()) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   // ************************************************************
    public static Boolean IsConectado() {
        Boolean resul = false;
        try {
            resul = (con != null) && (con.isValid(1000)); //System.out.println("Conectado");
            //System.out.println("NO conectado");
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        } // try         
        return resul;
    }

    /* ------------------- */
    /* EXISTE */
    /* ------------------- */
    public static boolean librosExiste(int id) {
        boolean resul = false;

        conectar();

        String sentenciaSQL = "SELECT * FROM libros WHERE id='" + id + "'";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /* ------------------- */
    /* GET ALL */
    /* ------------------- */
    public static ArrayList<Libros> librosGetAll() {
        ArrayList<Libros> lista = new ArrayList<>();

        conectar();
        String sentenciaSQL = "SELECT * FROM libros ORDER BY id";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            while (rs.next()) {
                lista.add(new Libros(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /* ------------------- */
    /* GET ONE */
    /* ------------------- */
    public static Libros librosGet(int id) {
        Libros libro = null;

        conectar();
        String sentenciaSQL = "SELECT * FROM libros WHERE id='" + id + "'";
        try (Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                libro = new Libros(rs.getInt("id"), rs.getString("titulo"), rs.getString("autor"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
        return libro;
    }

    /* ------------------- */
    /* POST */
    /* ------------------- */
    public static boolean librosPost(Libros libro) {
        boolean resul = false;

        conectar();
        if (libro != null) {
            String sentenciaSQL = "INSERT INTO libros (id, titulo, autor) VAlUES ('" + libro.getId() + "','" + libro.getTitulo() + "','" + libro.getAutor() + "')";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia INSERT de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        
        
        return resul;
    }

    /* ------------------- */
    /* PUT */
    /* ------------------- */
    public static boolean librosPut(Libros libro) {
        boolean resul = false;

        conectar();
        if (libro != null) {
            String sentenciaSQL = "UPDATE libros SET titulo='" + libro.getTitulo() + "', autor='" + libro.getAutor() + "' WHERE id='" + libro.getId() + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia UPDATE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }
    /* ------------------- */
    /* DELETE */
    /* ------------------- */
    public static boolean librosDelete(int id) {
        boolean resul = false;

        conectar();
        if (librosExiste(id)) {
            String sentenciaSQL = "DELETE FROM libros WHERE id='" + id + "'";
            try (Statement statement = con.createStatement()) {
                int cantidad = statement.executeUpdate(sentenciaSQL);
                if (cantidad == 1) {
                    resul = true;
                }
            } catch (SQLException ex) {
                System.out.println(" => Error en sentencia DELETE de SQL.");
                System.out.println("   => " + ex.getErrorCode() + " " + ex.getMessage());
            }
        }
        return resul;
    }

}
