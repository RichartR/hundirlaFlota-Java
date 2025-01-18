import java.util.Arrays;
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

    static String manualOAutomatico(){ //Elegir el tipo de modo de colocar barcos
        boolean modoValido = false;
        String modo;
        do {
            System.out.println("¿Quieres colocar los barcos de forma manual o automática? [M / A]" );
            modo = entradaDatos.nextLine().toUpperCase().trim();

            if (!modo.isEmpty() && (modo.substring(0, 1).equalsIgnoreCase("M") || 
            modo.substring(0, 1).equalsIgnoreCase("A"))) {
            modoValido = true;
        } else {
            System.out.println("No has introducido un modo correcto, inténtelo de nuevo.");
        }
        } while (!modoValido);

        modo = (modo.equals("M")) ? "Manual" : "Automático";

        return modo;
    }

    static int [] creacionIndiceModo(String [][] tablero, String modo){

        int [] casillaElegidaIndice = new int[2];
        if( modo.equals("Manual")) {
            String casillaElegidaUsuario = solicitarCasilla();
            casillaElegidaIndice = generarIndiceCasilla(casillaElegidaUsuario);
        } else {
            casillaElegidaIndice = posicionBarcoAleatorio(tablero);
        }

        return casillaElegidaIndice;
    }

    static String solicitarCasilla() { // Comprobar si la casilla existe en el tablero
        boolean letraCorrecta = false;
        boolean numeroCorrecto = false;
        String casillaElegida = "";
    
        do {
            System.out.println("Dime la casilla. (Por ejemplo: A5)");
    
            while (!entradaDatos.hasNextLine()) {
                System.out.println("No has introducido un modo correcto, inténtelo de nuevo.");
                entradaDatos.next();
                System.out.println("Dime la casilla. (Por ejemplo: A5)");
            }
            casillaElegida = entradaDatos.nextLine().trim().toUpperCase();
    
            // Longitud mínima
            if (casillaElegida.length() < 2) {
                System.out.println("La casilla es incorrecta, por favor inténtelo de nuevo.");
                continue; // Volver al inicio del bucle
            }
            
            letraCorrecta = false;
            for (int letra = 0; letra < letrasTablero.length; letra++) { //Validar letra
                if (casillaElegida.substring(0, 1).equalsIgnoreCase(letrasTablero[letra])) {
                    letraCorrecta = true;
                    break;
                }
            }
    
            
            numeroCorrecto = false;
            if (casillaElegida.length() == 2) { // Validar el número
                for (String numero : numerosTablero) {
                    if (casillaElegida.substring(1, 2).equalsIgnoreCase(numero)) {
                        numeroCorrecto = true;
                        break;
                    }
                }
            } else if (casillaElegida.length() > 2) {
                if (casillaElegida.substring(1).equals("10")) {
                    numeroCorrecto = true;
                }
            }
    
            if (!letraCorrecta || !numeroCorrecto) { // Mensaje error
                System.out.println("La casilla es incorrecta, por favor inténtelo de nuevo.");
            }
        } while (!letraCorrecta || !numeroCorrecto);
    
        return casillaElegida;
    }

    static int[] generarIndiceCasilla(String casillaElegida) {
        int[] indiceCasillas = new int[2];
        for (int letras = 0; letras < letrasTablero.length; letras++) { //Indice letras
            if (letrasTablero[letras].equals(casillaElegida.substring(0, 1))) {
                indiceCasillas[0] = letras + 1;
            }
        }
    
        String numeroParte = casillaElegida.substring(1); //Indice columnas
        for (int numeros = 0; numeros < numerosTablero.length; numeros++) {
            if (numerosTablero[numeros].equals(numeroParte)) {
                indiceCasillas[1] = numeros + 1;
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
            case "F":
                return "F";
        }
        return "";
    }

    static void cambiarTablero(int [] indiceCasilla, String[][] tablero, String casillaComprobada, String[][] tablero2){

        switch (casillaComprobada){
            case "~":
                System.out.println("¡Fallaste, solo había agua!");
                tablero[indiceCasilla[0]][indiceCasilla[1]] = "F";
                tablero2[indiceCasilla[0]][indiceCasilla[1]] = "F";
            case "B":
                System.out.println("¡Tocado!");
                tablero[indiceCasilla[0]][indiceCasilla[1]] = "T";
                tablero2[indiceCasilla[0]][indiceCasilla[1]] = "T";
            case "T":
                System.out.println("Esa casilla ya ha sido utilizada, por favor inténtelo de nuevo");
            case "F":
                System.out.println("Esa casilla ya ha sido utilizada, por favor inténtelo de nuevo");
        }
    }

    static int verticalOHorizontal(String modo){
        //Si sale 1 es Horizontal, si sale 0 es Vertical.
        int alineacion = 0;
        if (modo.equals("Manual")) {
            String alineacionTexto;
        boolean alineacionValida = false;
        do {
            System.out.println("¿Quieres colocar los barcos de forma manual o automática? [V / H]" );
            alineacionTexto = entradaDatos.nextLine().trim().toUpperCase(); // Elimina espacios en blanco
    
            // Verifica si la entrada no está vacía antes de usar substring
            if (!alineacionTexto.isEmpty() && (alineacionTexto.substring(0, 1).equalsIgnoreCase("V") || 
                alineacionTexto.substring(0, 1).equalsIgnoreCase("H"))) {
                alineacionValida = true;
            } else {
                System.out.println("No has introducido una alineación correcta, inténtelo de nuevo.");
            }
        } while (!alineacionValida);
    
            alineacion = (alineacionTexto.equals("V")) ? 1 : 0;

        } else {
            alineacion = (int)(Math.random() * 2); // De 0 a 1.
        }
        
        return alineacion;
    }

    static int [] posicionBarcoAleatorio(String [][] tablero){
        int [] posicionBarco = new int[2];
        posicionBarco[0] = (int)(Math.random()* tablero.length - 1) + 1; // Restamos 1 al array.lenght para que no de error de lenght.
        posicionBarco[1] = (int)(Math.random()* tablero.length - 1) + 1;

        return posicionBarco;
    }
    

    // Tenemos que crear un metodo con 6 barcos de diferentes tamaños.
    //1 Barco de tamaño 3
    //2 barcos de tamaño 2
    // y 3 barcos de tamaño 1

    public static int[][] cantidadBarcos(){ //Primero va el barco y después el tamaño
        int[] barcoPeque = {1,1,1}; // 3 barcos de tamaño 1
        int[] barcoMedio = {2,2}; //2 barcos de tamaño 2
        int[] barcoGrande = {3}; // 1 barco de tamaño 3
        return new int[][]{barcoPeque, barcoMedio, barcoGrande};
    
    }

    static boolean cabeONo(String tablero[][], int posicion[], int tamanyo, int verticalOHorizontal) {

        // Aquí vamos a intentar ver si el barco cabe, en caso de que no quepa
        // verticalmente hacia abajo o arriba, enviamos que NO CABE, igualmente si es
        // horizontal, vemos si cabe por abajo o por arriba, y si no es así, enviamos
        // QUE NO CABE.
        // También debo de tener en cuenta si hay un Barco de por medio, es decir que si
        // en esa casilla hay un B (de barco), no deberia de poder caber.

        // Recibo posicion[], que es un array que contiene la fila y la columna de forma
        // correspondiente.

        // Tenemos la variable tamaño (mas adelante debo de tratarlo, pero por ahora lo
        // dejaremos con una variable fija :D)
        int posicionFila = posicion[0];
        int posicionColumna = posicion[1];

        boolean cabe = true;

        // Horizontal
        if (verticalOHorizontal == 0) {
            if (posicionColumna + tamanyo > tablero[0].length) {
                cabe = false; // NO CABEEEE
            } else {
                for (int i = 0; i < tamanyo; i++) {
                    if (!tablero[posicionFila][posicionColumna + i].equals("~")) {
                        cabe = false; // Hay un barco
                        break;
                    }
                }
            } 
            
            // SI NO ES DERECHO, ES IZQUIERDO
            if (!cabe) {
                System.out.println("Hola");
                cabe = true; // Resetear para comprobar en la dirección contraria
                if (posicionColumna - tamanyo + 1 < 1) {
                    cabe = false; // No cabe hacia la izquierda
                }else {
                    for (int i = 0; i < tamanyo; i++) {
                        if (!tablero[posicionFila][posicionColumna - i].equals("~")) {
                            cabe = false; // Hay un barco en la dirección contraria
                            break;
                        }
                    }
                }
            }
        } else { // Vertical
            if (posicionFila + tamanyo > tablero.length) {
                cabe = false; // No cabe hacia abajo
            } else {
                for (int i = 0; i < tamanyo; i++) {
                    if (!tablero[posicionFila + i][posicionColumna].equals("~")) {
                        cabe = false; // Hay un barco
                        break;
                    }
                }
            }

            // SI NOES HACIA ABAJO, ES HACIA ARRIBA
            if (!cabe) {
                cabe = true; // Resetearrrrr
                if (posicionFila - tamanyo + 1 < 1) {
                    cabe = false; // No cabe hacia arribAAA
                } else {
                    for (int i = 0; i < tamanyo; i++) {
                        if (!tablero[posicionFila - i][posicionColumna].equals("~")) {
                            cabe = false; // Hay un barco en la dirección contraria :((()))
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(cabe);

        return cabe;
    }

    public static String[][] ColocarBarco(String tablero[][], int posicion[], int verticalOHorizontal, int tamanyo) {
        int posicionFila = posicion[0];
        int posicionColumna = posicion[1];
        // Si cabe, colocar el barco
        if (verticalOHorizontal == 0) { // Horizontal
            if (posicionColumna + tamanyo <= tablero[0].length) {
                for (int i = 0; i < tamanyo; i++) {
                    tablero[posicionFila][posicionColumna + i] = "B";
                }
            } else {
                if (posicionColumna - tamanyo + 1 >= 0) { // Asegurarse de que no salimos del tablero
                    for (int i = 0; i < tamanyo; i++) {
                        tablero[posicionFila][posicionColumna - i] = "B";
                    }
                } 
            }
        } else { // Vertical
            if (posicionFila + tamanyo <= tablero.length) {
                for (int i = 0; i < tamanyo; i++) {
                    tablero[posicionFila + i][posicionColumna] = "B";
                }
            } else {
                if (posicionFila - tamanyo + 1 >= 0) { // Asegurarse de que no salimos del tablero
                    for (int i = 0; i < tamanyo; i++) {
                        tablero[posicionFila - i][posicionColumna] = "B";
                    }
                }
            }
        }

        return tablero;
    }

    public static String[][] colocarBarcosAutomaticamente(String[][] tablero, String modo){
        int[] barcoPeque = cantidadBarcos()[0];
        int[] barcoMedio = cantidadBarcos()[1];
        int[] barcoGrande = cantidadBarcos()[2];
        
        for (int i = 0; i < barcoPeque.length; i++) {
            int[] posicion;
            int direccion;
            int tamanyo;
            if (modo.equalsIgnoreCase("Manual")) {
                System.out.println("Estás colocando "+ (i + 1) +" el barco tamaño 1");
            }

            do {
                posicion = creacionIndiceModo(tablero, modo); // Declarada fuera del bloque
                direccion = 1;
                tamanyo = barcoPeque[i];
                if (!cabeONo(tablero, posicion, tamanyo, direccion) && modo.equalsIgnoreCase("manual")) {
                    System.out.println("Posicion Incorrecta");
                }
            } while (!cabeONo(tablero, posicion, tamanyo, direccion)); 
            ColocarBarco(tablero, posicion, direccion, tamanyo);
            imprimirTablero(tablero);
        }
        for (int i = 0; i < barcoMedio.length; i++) {
            int[] posicion;
            int direccion;
            int tamanyo;
                if (modo.equalsIgnoreCase("manual")) {
                System.out.println("Estás colocando "+ (i + 1) +"el barco tamaño 2");
            }
            do {
                posicion = creacionIndiceModo(tablero, modo);; // Declarada fuera del bloque
                direccion = verticalOHorizontal(modo);
                tamanyo = barcoMedio[i];
                if (!cabeONo(tablero, posicion, tamanyo, direccion) && modo.equalsIgnoreCase("manual")) {
                    System.out.println("Posicion Incorrecta");
                }
            } while (!cabeONo(tablero, posicion, tamanyo, direccion)); 
            ColocarBarco(tablero, posicion, direccion, tamanyo);
            imprimirTablero(tablero);
        }
        for (int i = 0; i < barcoGrande.length; i++) {
            int[] posicion;
            int direccion;
            int tamanyo;
                    if (modo.equalsIgnoreCase("manual")) {
                System.out.println("Estás colocando"+ (i + 1) +"el barco tamaño 3");
            }
            do {
                posicion = creacionIndiceModo(tablero, modo);; // Declarada fuera del bloque
                direccion = verticalOHorizontal(modo);
                tamanyo = barcoGrande[i];
                                if (!cabeONo(tablero, posicion, tamanyo, direccion) && modo.equalsIgnoreCase("manual")) {
                    System.out.println("Posicion Incorrecta");
                }
            } while (!cabeONo(tablero, posicion, tamanyo, direccion)); 
            ColocarBarco(tablero, posicion, direccion, tamanyo);
            imprimirTablero(tablero);
        }
        
        return tablero;
    }

    public static void imprimirTablero(String tablero[][]){
        for (int filas = 0; filas < tablero.length; filas++) {
            for (int columnas = 0; columnas < tablero[filas].length; columnas++) {
                System.out.print(tablero[filas][columnas] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        String[][] tableroVisible = crearTableroVacio(letrasTablero, numerosTablero); // Tablero que ve la persona
        String[][] tableroNoVisible = crearTableroVacio(letrasTablero, numerosTablero); //Se colocan barcos no lo ve la persona

        System.out.println("===== Bienvenido a hundir la flota =====");
        System.out.println();

        String modoElegido = manualOAutomatico();
        
        imprimirTablero(colocarBarcosAutomaticamente(tableroNoVisible, modoElegido));
        

    }
}
