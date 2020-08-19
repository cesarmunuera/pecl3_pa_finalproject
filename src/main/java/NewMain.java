// Clase de prueba para el random

// Random se hace numAleatorio = (Math.random()* Max-Min+1) + Min);
public class NewMain {

    public static void main(String[] args) {
        while (true) {
            int numero = 0;
            numero = (int) (Math.random() * 20);
            System.out.println(numero);
        }
    }

}
