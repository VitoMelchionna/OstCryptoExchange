package ch.ost.aif.dialogflow.main;

import com.google.cloud.dialogflow.v2.QueryResult;

import java.util.Map;

public class ApiIntegrator {

    public final static String currency = "chf";

    public static String castResultIntoConsoleOutput(Map apiResult) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object key : apiResult.keySet())
        {
            stringBuilder.append("(current price: ");
            stringBuilder.append(currency.toUpperCase());
            stringBuilder.append(" ");
            stringBuilder.append(apiResult.get(key).toString().replaceAll("[^\\d\\.]+",""));
            stringBuilder.append(" per coin)");
            return stringBuilder.toString();
        }
        return "(coin price couldn't be fetched from API)";
    }

    public static String getCoinNameFromQuery(QueryResult queryResult){
        Map result = queryResult.getParameters().getFieldsMap();

        for (Object key : result.keySet())
        {
            if(key.equals("crypto-currency")){
                String[] splitCurrencyValue = result.get(key).toString().split(":");
                return splitCurrencyValue[1].replaceAll("[^a-zA-Z]", "");
            }
        }
        return "";
    }

    public static String convertCoinNameToApiParameter(String coinName){
        switch (coinName) {
            case "ADA":
                return "cardano";
            case "ETH":
                return "ethereum";
            default:
                return "";
        }
    }

}
