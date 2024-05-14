import service.APIService;
import service.MenuService;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean mainActive = true;
        // Crea el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        APIService apiService = new APIService();
        // Crea Scanner para lectura de opciones
        Scanner scanner = new Scanner(System.in);
        // Inicia el menu loop
        MenuService menuService = new MenuService();
        System.out.println("Bienvendio al converson de monedas");
        while (mainActive) {
            menuService.printMenuOptions();
            mainActive = menuService.menuOptions(client, scanner, apiService);
        }
    }
}