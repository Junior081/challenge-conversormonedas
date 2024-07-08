package conversor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConversorMonedas {
    private ApiClient apiClient = new ApiClient();
    private Scanner leer = new Scanner(System.in);
    private Map<String, Double> tasasDeCambio = new HashMap<>();

    public void iniciar() {
        try {
            cargarTasasDeCambio();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener las tasas de cambio. Intente nuevamente más tarde.");
            e.printStackTrace();
            return;
        }

        while (true) {
            System.out.println("Conversor Monedas");
            System.out.println("""
                    1-Soles peruanos a dolares
                    2-Pesos mexicanos a dolares
                    3-Pesos colombianos a dolares
                    4-Salir
                    """);
            System.out.println("Digite una opcion:");
            char opcion = leer.next().charAt(0);

            switch (opcion) {
                case '1':
                    convertir("PEN", "Soles peruanos");
                    break;
                case '2':
                    convertir("MXN", "Pesos Mexicanos");
                    break;
                case '3':
                    convertir("COP", "Pesos Colombianos");
                    break;
                case '4':
                    System.out.println("Cerrando programa");
                    return;
                default:
                    System.out.println("Opcion Incorrecta");
            }
        }
    }

    private void convertir(String codigoMoneda, String nombreMoneda) {
        double valorDolar = tasasDeCambio.getOrDefault(codigoMoneda, 1.0);
        System.out.printf("Digite la cantidad de %s: ", nombreMoneda);
        double cantidadMoneda = leer.nextDouble();
        double dolares = cantidadMoneda / valorDolar;

        dolares = Math.round(dolares * 100d) / 100d;

        System.out.println("************************");
        System.out.println("Tienes $" + dolares + " Dolares");
        System.out.println("***************************");
    }

    private void cargarTasasDeCambio() throws IOException, InterruptedException {
        String jsonResponse = apiClient.obtenerTasasDeCambio();
        tasasDeCambio = apiClient.parsearTasasDeCambio(jsonResponse);
    }
}
