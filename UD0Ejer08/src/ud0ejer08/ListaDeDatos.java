/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ud0ejer08;

import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class ListaDeDatos {
    private ArrayList lista;
    public ListaDeDatos(){
        lista = new ArrayList<String>();
    }
    public void Incluir(String texto){
        lista.add(texto);
    }
    public boolean Contiene(String texto){
        return lista.contains(texto);
    }
    public void mostrarDatosOrdenados(){
       for(var valor : lista){
           System.out.println(valor);
       }
    }
}
