package ch.ost.aif.dialogflow.coingecko;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class CoinGeckoApi {

    final String apiBaseURL = "https://api.coingecko.com/api/v3/simple/price";

    public CoinGeckoApi(){}

    public Map<String, Object> getCoinPrice(String coin, String referenceCurrency)
    {
        try {
            String queryString = "?ids=" + coin + "&vs_currencies=" + referenceCurrency;
            URL request = new URL(apiBaseURL + queryString);
            URLConnection tc = request.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            ObjectMapper objectMapper = new ObjectMapper();
            Map result = objectMapper.readValue(in.readLine(), new TypeReference<Map<String,Object>>(){});
            in.close();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
