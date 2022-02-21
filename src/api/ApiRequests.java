package api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class ApiRequests {

    public static void main(String[] args) {
//        ApiRequests requests = new ApiRequests();
//        try {
//            ActualityCurrency test = requests.getSingleCurrenySpecificDate("eur", "2021-12-01");
//            System.out.println(test);
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
        ArrayList<ArrayList<String>> intervalsToProcess = new ArrayList<ArrayList<String>>();

        LocalDate DateDateStart = LocalDate.parse("2020-12-01");
        LocalDate DateDateStop = LocalDate.parse("2021-12-30");

        if (Duration.between(DateDateStart.atStartOfDay(), DateDateStop.atStartOfDay()).toDays() > 367) {
            LocalDate lastStartDate = DateDateStart;
            while (true) {
                ArrayList<String> currentInverval = new ArrayList<String>();
                currentInverval.add(lastStartDate.toString());
                LocalDate currentStopDate = lastStartDate.plusDays(365);
                currentInverval.add(currentStopDate.toString());
                intervalsToProcess.add(currentInverval);
                if (DateDateStop.isBefore(currentStopDate.plusDays(365))) {
                    ArrayList<String> lastInterval = new ArrayList<String>();
                    lastInterval.add(currentStopDate.plusDays(1).toString());
                    lastInterval.add(DateDateStop.toString());
                    intervalsToProcess.add(lastInterval);
                    break;
                }
                lastStartDate = currentStopDate.plusDays(1);
            }
        }
        System.out.println(intervalsToProcess);
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {

        URL url2 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        InputStream responseStream = connection.getInputStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            responseStream.close();
        }
    }

    public JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {

        URL url2 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        InputStream responseStream = connection.getInputStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            responseStream.close();
        }
    }

    public CurrencyFull getSingleFullCurreny(String code) throws IOException, JSONException {

        JSONObject json = readJsonObjectFromUrl(String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/", code));

        JSONArray rates = (JSONArray) json.get("rates");
        JSONObject ratesObj = (JSONObject) rates.get(0);

        double buyValue = ((BigDecimal) ratesObj.get("bid")).doubleValue();
        double sellValue = ((BigDecimal) ratesObj.get("ask")).doubleValue();
        String effectiveDate = (String) ratesObj.get("effectiveDate");

        json.put("sellValue", sellValue);
        json.put("buyValue", buyValue);
        json.put("effectiveDate", effectiveDate);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CurrencyFull currency = objectMapper.readValue(json.toString(), CurrencyFull.class);

        return currency;
    }

    public ActualityCurrency getSingleCurrenySpecificDate(String code, String date, boolean linkChange) throws IOException, JSONException {

        JSONObject json1;
        if (!linkChange) {
            json1 = readJsonObjectFromUrl(String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/%s/", code, date));
        } else {
            json1 = readJsonObjectFromUrl(String.format("http://api.nbp.pl/api/exchangerates/rates/c/%s/last/", code));
        }
        JSONArray rates1 = (JSONArray) json1.get("rates");
        JSONObject ratesObj1 = (JSONObject) rates1.get(0);

        double buyValue = ((BigDecimal) ratesObj1.get("bid")).doubleValue();
        double sellValue = ((BigDecimal) ratesObj1.get("ask")).doubleValue();

        JSONObject json2;
        if (!linkChange) {
            json2 = readJsonObjectFromUrl(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/", code, date));
        } else {
            json2 = readJsonObjectFromUrl(String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/last/", code));
        }

        JSONArray rates2 = (JSONArray) json2.get("rates");
        JSONObject ratesObj2 = (JSONObject) rates2.get(0);

        double average = ((BigDecimal) ratesObj2.get("mid")).doubleValue();

        JSONObject finalJson = new JSONObject();
        finalJson.put("selling", sellValue);
        finalJson.put("buying", buyValue);
        finalJson.put("average", average);
        finalJson.put("name", code.toUpperCase(Locale.ROOT));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ActualityCurrency currency = objectMapper.readValue(finalJson.toString(), ActualityCurrency.class);

        return currency;
    }
}
