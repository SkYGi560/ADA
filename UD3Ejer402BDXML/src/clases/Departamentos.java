/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Departamentos {

    public static ArrayList<Departamento> creaLista(){
        var departamentos = new ArrayList<Departamento>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url;
            url = "jdbc:mysql://localhost:3306/bddepartamentos";
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";
            String usuario = "root";
            String password = "1234";
            String sentenciaSQL = "SELECT * FROM departamentos";
            try (
                    Connection con = DriverManager.getConnection(url, usuario, password); 
                    Statement statement = con.createStatement(); 
                    ResultSet rs = statement.executeQuery(sentenciaSQL);
                    ){
                while(rs.next()){
                    departamentos.add(new Departamento(rs.getInt(1),rs.getString(2),rs.getString(3)));
                }
            }catch(SQLException se){
                System.out.println(se.getErrorCode() + " " + se.getMessage());
            }
        } catch(ClassNotFoundException ce){
            ce.printStackTrace();
        }
        return departamentos;
    }
}
