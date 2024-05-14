package service;

import model.CurrencyModel;

import java.net.http.HttpClient;
import java.util.Scanner;

public class MenuService {

    CurrencyModel baseCurrency = new CurrencyModel();
    CurrencyModel targetCurrency = new CurrencyModel();
    public void printMenuOptions() {
        System.out.println("\nSeleccione una opcion: \n" +
                "\n1) Dólar ==> Peso argentino\n" +
                "2) Peso argentino ==> Dólar\n" +
                "3) Dólar ==> Peso mexicano\n" +
                "4) Peso mexicano ==> Dólar\n" +
                "5) Dólar ==> Real brasileño\n" +
                "6) Real brasileño ==> Dólar\n" +
                "7) Dólar ==> Peso colombiano\n" +
                "8) Peso colombiano ==> Dólar\n" +
                "9) Perzonalizar tu conversion\n" +
                "0) Finalizar\n");
    }

    private void printSpecialMenu() {
        System.out.println("\nSeleccione una opcion:\n" +
                "\n1) Ver los codigos de los tipos de cambio disponibles\n" +
                "2) Fijar los valores a convertir\n" +
                "3) Regresar al menu princial\n");
    }

    public boolean menuOptions(HttpClient client, Scanner scanner, APIService apiService) {
        int opt = scanner.nextInt();
        if (opt == 1) {
            baseCurrency.setCode("USD");
            targetCurrency.setCode("ARS");
        } else if (opt == 2) {
            baseCurrency.setCode("ARS");
            targetCurrency.setCode("USD");
        } else if (opt == 3) {
            baseCurrency.setCode("USD");
            targetCurrency.setCode("MXN");
        } else if (opt == 4) {
            baseCurrency.setCode("MXN");
            targetCurrency.setCode("USD");
        } else if (opt == 5) {
            baseCurrency.setCode("USD");
            targetCurrency.setCode("BRL");
        } else if (opt == 6) {
            baseCurrency.setCode("BRL");
            targetCurrency.setCode("USD");
        } else if (opt == 7) {
            baseCurrency.setCode("USD");
            targetCurrency.setCode("COP");
        } else if (opt == 8) {
            baseCurrency.setCode("COP");
            targetCurrency.setCode("USD");
        } else if (opt == 9) {
            boolean secActive = true;
            while (secActive) {
                printSpecialMenu();
                secActive = specialMenu(client, apiService);
            }
        } else if (opt == 0) {
            System.out.println("Gracias por usar nuestros servicios");
            return false;
        } else {
            System.out.println("Opcion no valida, vuelva a elegir");
            menuOptions(client, scanner, apiService);
        }
        if (opt != 9) {
            System.out.println("cuantos " + baseCurrency.getCode() + " deseas convertir a " + targetCurrency.getCode());
            int amount = scanner.nextInt();
            apiService.requestPairConversion(client, baseCurrency.getCode(), targetCurrency.getCode(), amount);
        }
        return true;
    }

    private boolean specialMenu(HttpClient client, APIService apiService) {
        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();
        if (opt == 1) {
            apiService.requestSupportedCurrencies(client);
        } else if (opt == 2) {
            System.out.println("Escribe el codigo la moneda que te interesa convertir");
            Scanner bscanner = new Scanner(System.in);
            baseCurrency.setCode(bscanner.nextLine());
            System.out.println("Escribe el codigo del valor a convetir");
            Scanner tscanner = new Scanner(System.in);
            targetCurrency.setCode(tscanner.nextLine());
        } else if (opt == 3) {
            return false;
        } else {
            System.out.println("Opcion no valida, vuelva a elegir");
            specialMenu(client, apiService);
        }
        if (opt == 2) {
            System.out.println("cuantos " + baseCurrency.getCode() + " deseas convertir a " + targetCurrency.getCode());
            int amount = scanner.nextInt();
            apiService.requestPairConversion(client, baseCurrency.getCode(), targetCurrency.getCode(), amount);
        }
        return true;
    }
}
