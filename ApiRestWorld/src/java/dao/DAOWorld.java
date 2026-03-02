package dao;

import clases.City;
import clases.Country;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOWorld {

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
                url = "jdbc:mysql://localhost:3306/worldnew";
                url += "?zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&serverTimezone=UTC";

                String usuario = "root";
                String password = "1234";
                con = DriverManager.getConnection(url, usuario, password);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   // ************************************************************
    public static Boolean IsConectado() {
        Boolean resul = false;
        try {
            resul = (con != null) && (con.isValid(1000)); //System.out.println("Conectado");
            //System.out.println("NO conectado");
        } catch (SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        } // try          // try         
        return resul;
    }

    /* ------------------- */
    /* EXISTE */
    /* ------------------- */
    public static boolean cityExiste(int id) {
        boolean resul = false;

        conectar();

        String sentenciaSQL = "SELECT * FROM city WHERE id='" + id + "'";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                resul = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resul;
    }

    /* ------------------- */
    /* GET ALL */
    /* ------------------- */
    public static ArrayList<City> cityGetAll() {
        ArrayList<City> lista = new ArrayList<>();

        conectar();
        String sentenciaSQL = "SELECT * FROM city ORDER BY id";
        try (Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            while (rs.next()) {
                lista.add(new City(rs.getInt("id"), rs.getString("name"), rs.getString("district"),rs.getInt("population"),rs.getString("countrycode")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    /* ------------------- */
    /* GET ONE */
    /* ------------------- */
    public static City cityGet(int id) {
        City city = null;

        conectar();
        String sentenciaSQL = "SELECT * FROM city WHERE id='" + id + "'";
        try (Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                city = new City(rs.getInt("id"), rs.getString("name"), rs.getString("district"),rs.getInt("population"),rs.getString("countrycode"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return city;
    }

    public static boolean countryExiste(String code) {
        boolean resul = false;
        for (City city : cityGetAll()) {
            if (city.getCountrycode().equals(code)) {
                resul = true;
            }
        }
        return resul;
    }
    /* ------------------- */
    /* GET ONE */
    /* ------------------- */
    public static Country countryGet(String code) {
        Country country = null;
        ArrayList<City> listaCities = new ArrayList<>();
        conectar();
        String sentenciaSQL = "SELECT * FROM country WHERE code='" + code + "'";
        try (Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sentenciaSQL);) {
            if (rs.next()) {
                for(var city : cityGetAll()){
                    if(city.getCountrycode().equals(rs.getString("code"))){
                        listaCities.add(city);
                    }
                }
                country = new Country(
                        rs.getString("code"), 
                        rs.getString("name"), 
                        rs.getString("continent"), 
                        rs.getDouble("surfacearea"), 
                        cityGet(rs.getInt("capital")), 
                        listaCities);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOWorld.class.getName()).log(Level.SEVERE, null, ex);
        }
        return country;
    }

}
