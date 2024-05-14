package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.TargetCurrencyModel;
import model.UserEntityModel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIService {
    UserEntityModel userEntityModel = new UserEntityModel();
    TargetCurrencyModel targetCurrency = new TargetCurrencyModel();
    public void requestSupportedCurrencies(HttpClient client) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/" + userEntityModel.getToken() + "/codes"))
                .build();
        HttpResponse<String> response = responseRequest(client, request);
        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray supportedCodes = jsonObject.getAsJsonArray("supported_codes");

        for (int i = 0; i < supportedCodes.size(); i++) {
            JsonArray currency = supportedCodes.get(i).getAsJsonArray();
            String code = currency.get(0).getAsString();
            String name = currency.get(1).getAsString();
            System.out.println("Code: " + code + ", Name: " + name);
        }
    }
    public void requestPairConversion(HttpClient client, String baseCode, String targetCode, int amount) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/" + userEntityModel.getToken() + "/pair/" + baseCode + "/" + targetCode + "/" + amount))
                .build();
        HttpResponse<String> response = responseRequest(client, request);
        Gson gson = new Gson();
        this.targetCurrency = gson.fromJson(response.body(), TargetCurrencyModel.class);
        System.out.println("El equivalente de " + amount + " " + baseCode + " a " + targetCode + " es de: " + this.targetCurrency.getConversionResult());
    }

    private HttpResponse<String> responseRequest(HttpClient client, HttpRequest request) {
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (IOException | InterruptedException e) {
            System.out.println("Error detectado: " + e);
            return null;
        }
    }
}
