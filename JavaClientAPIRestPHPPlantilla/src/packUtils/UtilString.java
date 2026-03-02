
package packUtils;

public class UtilString {
    
    public static String StrRepetir(char c, int n) {
        String resul="";
        for (int x=1; x<=n; x++) {
            resul = resul + c;
        }
        return resul;
    }
    
    
    public static void Linea() {
        System.out.println(Colores.AZULxAMARILLO + StrRepetir('-',40));
    }    
}
