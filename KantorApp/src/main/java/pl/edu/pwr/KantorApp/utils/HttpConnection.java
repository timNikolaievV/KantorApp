package pl.edu.pwr.KantorApp.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

//stronki z ktorych korzystalem
//        https://exchangeratesapi.io/
//        https://api.exchangeratesapi.io/latest?base=USD

public class HttpConnection {

    public double getRate (String currency1, String currency2) throws IOException {
        String ratesHttpApi = System.getProperty("rate.http.api");
        String ulrForRates = ratesHttpApi + currency1;
        double getRate;

        URL url = new URL(ulrForRates);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = (JsonObject) root.getAsJsonObject().get("rates");
        getRate = jsonobj.get(currency2).getAsDouble();

        return getRate;
    }
}