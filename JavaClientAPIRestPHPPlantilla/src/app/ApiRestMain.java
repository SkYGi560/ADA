package app;

import java.io.StringReader;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import packUtils.Menu;

public class ApiRestMain {

    public static void main(String[] args) {
        Menu.Mostrar();
    }

    public static void getOne() {
        Client client = ClientBuilder.newClient();

        Scanner teclado = new Scanner(System.in);
        System.out.print("Escriba el ID del registro: ");
        int registro = Integer.valueOf(teclado.nextLine());

        // Response response1 = client
        //        .target()
        //        .path()
        //        .queryParam()
        //        .request()
        //        .header()
        //        .accept()
        //        .XXXXX();
        System.out.println("status:" + response1.getStatus());

        if (response1.getStatus() == 200) {
            String body = response1.readEntity(String.class);
            //System.out.println("Response: " + body);

            JsonReader jsonReader = Json.createReader(new StringReader(body));
            JsonArray jsonArray = jsonReader.readArray();

            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            System.out.printf("|   id   |   fecha    | texto                                                                  |\n");
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            for (JsonValue jsonValue : jsonArray) {
                JsonObject json = (JsonObject) jsonValue;
                //System.out.println("El json:" + json.toString());

                System.out.printf("| %6d | %10s | %-70s |\n",
                        json.getInt("id"),
                        json.getString("fecha"),
                        json.getString("texto"));
            }
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
        }

    }

    public static void getTodos() {
        Client client = ClientBuilder.newClient();

        // Response response1 = client
        //        .target()
        //        .path()
        //        .queryParam()
        //        .request()
        //        .header()
        //        .accept()
        //        .XXXXX();
        System.out.println("status:" + response1.getStatus());

        if (response1.getStatus() == 200) {
            String body = response1.readEntity(String.class);
            //System.out.println("Response: " + body);

            JsonReader jsonReader = Json.createReader(new StringReader(body));
            JsonArray jsonArray = jsonReader.readArray();

            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            System.out.printf("|   id   |   fecha    | texto                                                                  |\n");
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            for (JsonValue jsonValue : jsonArray) {
                JsonObject json = (JsonObject) jsonValue;
                //System.out.println("El json:" + json.toString());

                System.out.printf("| %6d | %10s | %-70s |\n",
                        json.getInt("id"),
                        json.getString("fecha"),
                        json.getString("texto"));
            }
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
        }

    }

    public static void insert() {
        Client client = ClientBuilder.newClient();

        Scanner teclado = new Scanner(System.in);

        System.out.println("Escriba los datos del nuevo registro");
        System.out.println("====================================");

        System.out.print("XXXXXX: ");
        String dato1 = teclado.nextLine();

        System.out.print("XXXXXX: ");
        String dato2 = teclado.nextLine();

        // Response response1 = client
        //        .target()
        //        .path()
        //        .queryParam()
        //        .request()
        //        .header()
        //        .accept()
        //        .XXXXX();
        System.out.println("status:" + response1.getStatus());
        String body = response1.readEntity(String.class);
        System.out.println("Response: " + body);

        if (response1.getStatus() == 201) {
            System.out.println("REGISTRO INSERTADO");
        }
    }

    public static void update() {
        Client client = ClientBuilder.newClient();

        Scanner teclado = new Scanner(System.in);
        System.out.print("Escriba el ID del registro: ");
        int registro = Integer.valueOf(teclado.nextLine());

        // ******************************
        // BUSCAR REGISTRO A ACTUALIZAR
        // ******************************
        // Response response1 = client
        //        .target()
        //        .path()
        //        .queryParam()
        //        .request()
        //        .header()
        //        .accept()
        //        .XXXXX();
        if (response1.getStatus() == 200) {
            String body = response1.readEntity(String.class);
            //System.out.println("Response: " + body);

            JsonReader jsonReader = Json.createReader(new StringReader(body));
            JsonArray jsonArray = jsonReader.readArray();

            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            System.out.printf("|   id   |   fecha    | texto                                                                  |\n");
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");
            for (JsonValue jsonValue : jsonArray) {
                JsonObject json = (JsonObject) jsonValue;
                //System.out.println("El json:" + json.toString());

                System.out.printf("| %6d | %10s | %-70s |\n",
                        json.getInt("id"),
                        json.getString("fecha"),
                        json.getString("texto"));
            }
            System.out.printf("+--------+------------+------------------------------------------------------------------------+\n");

            System.out.println("Escriba los nuevos datos del registro");
            System.out.println("=====================================");

            System.out.print("XXXXXX: ");
            String dato1 = teclado.nextLine();

            System.out.print("XXXXXX: ");
            String dato2 = teclado.nextLine();

            // Response response1 = client
            //        .target()
            //        .path()
            //        .queryParam()
            //        .request()
            //        .header()
            //        .accept()
            //        .XXXXX();
            System.out.println("status:" + response1.getStatus());
            String body = response1.readEntity(String.class);
            System.out.println("Response: " + body);

            if (response1.getStatus() == 201) {
                System.out.println("REGISTRO ACTUALIZADO");
            }
        } else {
            String body = response1.readEntity(String.class);
            System.out.println("Response: " + body);
            System.out.println("ERROR");
        }
    }

    public static void delete() {
        Client client = ClientBuilder.newClient();

        Scanner teclado = new Scanner(System.in);
        System.out.print("Escriba el ID del registro: ");
        int registro = Integer.valueOf(teclado.nextLine());

        // Response response1 = client
        //        .target()
        //        .path()
        //        .queryParam()
        //        .request()
        //        .header()
        //        .accept()
        //        .XXXXX();
        if (response1.getStatus() == 200) {
            System.out.println("status:" + response1.getStatus());
            String body = response1.readEntity(String.class);
            System.out.println("Response: " + body);

            if (response1.getStatus() == 201) {
                System.out.println("REGISTRO ACTUALIZADO");
            }
        }

    }

}
