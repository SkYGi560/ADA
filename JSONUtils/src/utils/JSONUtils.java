package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {

    public static JSONObject getObjectFromString(String jsonObjectStr) {
        JSONObject json;
        json = new JSONObject(jsonObjectStr);
        return json;
    }
    public static JSONArray getArrayFromString(String jsonObjectStr) {
        JSONArray json;
        json = new JSONArray(jsonObjectStr);
        return json;
    }

    public static JSONObject getObjectFromFile(String strFile) {
        JSONObject json;
        json = new JSONObject(getStringFromFile(strFile));
        return json;
    }
    public static JSONArray getArrayFromFile(String strFile) {
        JSONArray json;
        json = new JSONArray(getStringFromFile(strFile));
        return json;
    }

    public static JSONObject getObjectFromUrl(String strConnection) {
        JSONObject json;
        json = new JSONObject(getStringFromUrl(strConnection));
        return json;
    }
    public static JSONArray getArrayFromUrl(String strConnection) {
        JSONArray json;
        json = new JSONArray(getStringFromUrl(strConnection));
        return json;
    }

    public static String getStringFromFile(String strFile) {
        // Lectura completa
        String jsonText = "";

        try {
            jsonText = new String(Files.readAllBytes(Paths.get(strFile)),
                    StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return jsonText;
    }

    public static String getStringFromUrl(String strConnection) {
        String jsonText = "";
        try {
            // Conexión sin proxy
            URL url = new URL(strConnection);
            URLConnection uc = url.openConnection();
            HttpURLConnection conn = (HttpURLConnection) uc;
            // La conexión se va a realizar para poder enviar y recibir
            // información en formato JSON
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            // Se va a realizar una petición con el método GET
            conn.setRequestMethod("GET");
            // Ejecutar la conexión y obtener la respuesta
            try (
                    InputStreamReader rd = new InputStreamReader(conn.getInputStream()); BufferedReader bf = new BufferedReader(rd)) {

                String str;
                while ((str = bf.readLine()) != null) {
                    jsonText += str.trim();
                }
            }
        } catch (MalformedURLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        return jsonText;
    }

}
