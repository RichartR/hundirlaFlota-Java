import java.util.Scanner;

public class hundirLaFlota {
    static Scanner entradaDatos = new Scanner(System.in);
    static String [] letrasTablero = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}; // Nombre filas
    static String [] numerosTablero = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}; //Nombre columnas

    static String [][] crearTableroVacio(String [] letrasTablero, String [] numerosTablero){ //Creamos el tablero con todo agua, sin barcos
        String [][] tableroVacio = new String[11][11];
        

        for (int filas = 0; filas < tableroVacio.length; filas++) {
            for (int columnas = 0; columnas < tableroVacio[filas].length; columnas++) {
                if (filas == 0 && columnas == 0){
                    tableroVacio[filas][columnas] = " ";
                } else if (filas == 0) {
                    tableroVacio[filas][columnas] = numerosTablero[columnas-1];
                }
                else if (columnas == 0) {
                    tableroVacio[filas][columnas] = letrasTablero[filas-1];
                } else {
                    tableroVacio[filas][columnas] = "~";
                }
            }
        }

        return tableroVacio;
    }

    static String solicitarCasilla(){
        System.out.println("Dime la casilla. (Por ejemplo: A5)");
        String casillaElegida = entradaDatos.nextLine();

        //FALTA. Comprobar si la casilla existe, es decir, que esté dentro de los valores del tablero

        return casillaElegida;
    }

    static String comprobarCasilla(String casillaUsuario){
        String casillaUsuarioComprobada = "";

        //comprobar si esta o no ocupada
        // Para comprobarlo hacerlo con substrings no con charAt
        
        System.out.println(casillaUsuario.substring(0, 1));

        return casillaUsuarioComprobada;
    }
    

    public static void main(String[] args) {
        System.out.println(comprobarCasilla(solicitarCasilla()));

        String [][] tableroVacio = crearTableroVacio(letrasTablero, numerosTablero);
        for (int i = 0; i < tableroVacio.length; i++) {
            for (int j = 0; j < tableroVacio[i].length; j++) {
                System.out.print(tableroVacio[i][j] + " ");
            }
            System.out.println("");
        }
    }
}