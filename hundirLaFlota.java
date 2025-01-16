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

    static String solicitarCasilla(){ //Compribar si la casilla existe en el tablero
        boolean letraCorrecta = false;
        boolean numeroCorrecto = false;
        String casillaElegida = "";
        
        System.out.println("Dime la casilla. (Por ejemplo: A5)");
        while (letraCorrecta != true || numeroCorrecto != true) {
            casillaElegida = entradaDatos.nextLine();
            for (int letra = 0; letra < letrasTablero.length; letra++) { //Comprueba si existe la fila
                if (casillaElegida.substring(0, 1).equalsIgnoreCase(letrasTablero[letra])) {
                    letraCorrecta = true;
                    break;
                }
            }
        
            for (int numero = 0; numero < letrasTablero.length; numero++) { //Comprueba si existe la columna
                if (casillaElegida.substring(1, 2).equalsIgnoreCase(numerosTablero[numero])) {
                    numeroCorrecto = true; 
                    break;
                }
            }

            System.out.println(letraCorrecta != true || numeroCorrecto != true ?"La casilla es incorrecta, por favor inténtelo de nuevo.": "");
        }

        return casillaElegida;
    }

    static int [] indiceCasilla(String casillaElegida) {
        int [] indiceCasillas = new int [2];

        for (int letras = 0; letras < letrasTablero.length; letras++) { //Sacar indice filas
            if (letrasTablero[letras].equals(casillaElegida.substring(0, 1))) {
                indiceCasillas[0] = letras +1;
            }
        }

        for (int numeros = 0; numeros < numerosTablero.length; numeros++) { //Sacar indice filas
            if (numerosTablero[numeros].equals(casillaElegida.substring(1, 2))) {
                indiceCasillas[1] = numeros +1;
            }
        }

        return indiceCasillas;
    }

    static String comprobarCasilla(int [] indiceCasillas, String [][] tablero){ //Falta comprobar que contenido tiene la casilla
        
        switch (tablero[indiceCasillas[0]][indiceCasillas[1]]) {
            case "~":
                return "~";
            case "B":
                return "B";
            case "T":
                return "T";
            case "H":
                return "H";
        }
        return "";
    }

    static int verticalOHorizontal(){
        //Si sale 0 es Vertical, si sale 1 es Horizontal.
        int alineacion = (int)(Math.random() * 2); // De 0 a 1.
        
        System.out.println("test, salió " + alineacion);
        return alineacion;
    }


    static int posicionIndiceBarcoAleatorio(int posicion, array[][]){
        int posicionBarco = (int)(Math.random()* array.length - 1) + 1; // Restamos 1 al array.lenght para que no de error de lenght.

        return posicionBarco;
    }

    static String [][] asignacionIndiceBarco(String array[][], int posicionFila, int posicionColumna){

        int tamanyo = 3;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if ( i == posicionFila && j == posicionColumna) {
                    if (verticalOHorizontal() == 0) { // 0 es Vertical, 1 es Horizontal
                        for (int h = 0; h < tamanyo - 1; h++) {
                            array[i + h][j] = "B";
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        String [][] tableroVacio = crearTableroVacio(letrasTablero, numerosTablero);
        // for (int filas = 0; filas < tableroVacio.length; filas++) {
        //     for (int columnas = 0; columnas < tableroVacio[filas].length; columnas++) {
        //         System.out.print(tableroVacio[filas][columnas] + " ");
        //     }
        //     System.out.println("");
        // }

        String casillaElegida = solicitarCasilla();
        System.out.println(casillaElegida);
        System.out.println(casillaElegida.substring(0,1));
        System.out.println(casillaElegida.substring(1,2));

    
    }
}
