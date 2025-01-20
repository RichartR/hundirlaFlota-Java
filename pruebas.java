import java.util.Scanner;

public class pruebas {
    static Scanner entradaDatos = new Scanner(System.in);
    static String[] letrasTablero = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    static String[] numerosTablero = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    static String[][] crearTableroVacio() { //Generar tablero base
        String[][] tablero = new String[11][11];
        for (int filas = 0; filas < tablero.length; filas++) {
            for (int columnas = 0; columnas < tablero[filas].length; columnas++) {
                tablero[filas][columnas] = (filas == 0 && columnas == 0) ? " " : (filas == 0) ? numerosTablero[columnas - 1] : (columnas == 0) ? letrasTablero[filas - 1] : "~";
            }
        }
        return tablero;
    }

    static String elegirModo() { //Elegir el modo de colocar los barcos
        boolean modoValido = false;
        String modo;
        do {
            System.out.println("¿Colocar barcos manual o automático? [M / A]");
            modo = entradaDatos.nextLine().trim().toUpperCase();
            if (!modo.isEmpty() && (modo.substring(0, 1).equalsIgnoreCase("M") ||
                    modo.substring(0, 1).equalsIgnoreCase("A"))) {
                modoValido = true;
            } else {
                System.out.println("No has introducido un modo correcto, inténtelo de nuevo.");
            }
        } while (!modoValido);
        
        return modo.substring(0, 1).equalsIgnoreCase("M") ? "Manual" : "Automático";
    }

    static int[] generarIndice(String[][] tablero, String modo) { //Generar índice de la casilla dependiendo del modo
        return modo.equals("Manual") ? generarIndiceUsuario() : posicionAleatoria(tablero);
    }

    static int[] generarIndiceUsuario() { //En base a la casilla del usuario
        String casilla;
        int[] indices = new int[2];
        do {
            System.out.println("Dime la casilla. (Por ejemplo: A5)");
            casilla = entradaDatos.nextLine().trim().toUpperCase();
        } while (!validarCasilla(casilla, indices));
        return indices;
    }

    static boolean validarCasilla(String casilla, int[] indices) {
        if (casilla.length() < 2) return false;
        String letra = casilla.substring(0, 1);
        String numero = casilla.substring(1);
        for (int i = 0; i < letrasTablero.length; i++) {
            if (letrasTablero[i].equals(letra)) {
                indices[0] = i + 1;
                break;
            }
        }
        for (int i = 0; i < numerosTablero.length; i++) {
            if (numerosTablero[i].equals(numero)) {
                indices[1] = i + 1;
                break;
            }
        }
        return indices[0] > 0 && indices[1] > 0;
    }

    static int[] posicionAleatoria(String[][] tablero) {
        return new int[]{(int) (Math.random() * 10) + 1, (int) (Math.random() * 10) + 1};
    }

    static boolean validarEspacio(String[][] tablero, int[] posicion, int tamano, boolean esVertical) {
        int fila = posicion[0], columna = posicion[1];
        if (esVertical && fila + tamano > tablero.length) return false;
        if (!esVertical && columna + tamano > tablero[0].length) return false;
        for (int i = 0; i < tamano; i++) {
            if (!tablero[esVertical ? fila + i : fila][esVertical ? columna : columna + i].equals("~")) return false;
        }
        return true;
    }

    static void colocarBarco(String[][] tablero, int[] posicion, int tamano, boolean esVertical) {
        for (int i = 0; i < tamano; i++) {
            if (esVertical) {
                tablero[posicion[0] + i][posicion[1]] = "B";
            } else {
                tablero[posicion[0]][posicion[1] + i] = "B";
            }
        }
    }

    static void colocarBarcos(String[][] tablero, String modo) {
        int[] barcos = {1, 1, 1, 2, 2, 3};
        for (int tamano : barcos) {
            int[] posicion;
            boolean esVertical;
            do {
                posicion = generarIndice(tablero, modo);
                esVertical = modo.equals("Manual") ? elegirDireccion() : Math.random() < 0.5;
            } while (!validarEspacio(tablero, posicion, tamano, esVertical));
            colocarBarco(tablero, posicion, tamano, esVertical);
        }
    }

    static boolean elegirDireccion() {
        String direccion;
        do {
            System.out.println("¿Vertical o Horizontal? [V / H]");
            direccion = entradaDatos.nextLine().trim().toUpperCase();
        } while (!direccion.equals("V") && !direccion.equals("H"));
        return direccion.equals("V");
    }

    static void imprimirTablero(String[][] tablero) {
        for (String[] fila : tablero) {
            System.out.println(String.join(" ", fila));
        }
    }

    public static void main(String[] args) {
        System.out.println("===== Bienvenido a Hundir la Flota =====");
        String[][] tableroJugador = crearTableroVacio();
        String[][] tableroOculto = crearTableroVacio();

        String modo = elegirModo();
        colocarBarcos(tableroOculto, modo);

        System.out.println("\n¡Comienza el juego!");
        imprimirTablero(tableroJugador);
    }
}
