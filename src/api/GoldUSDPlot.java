package api;

import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class GoldUSDPlot {

    private String dateStart;
    private String dateStop;
    public DefaultCategoryDataset dataset;
    public double CurrencyMinRate;
    public double CurrencyMaxRate;

    public GoldUSDPlot(String dateStart, String dateStop) {
        this.dateStart = dateStart;
        this.dateStop = dateStop;

        ArrayList<ArrayList<String>> intervalsToProcess = new ArrayList<ArrayList<String>>();
        LocalDate DateDateStart = LocalDate.parse(dateStart);
        LocalDate DateDateStop = LocalDate.parse(dateStop);

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
        }  else {
            ArrayList<String> currentInverval = new ArrayList<String>();
            currentInverval.add(DateDateStart.toString());
            currentInverval.add(DateDateStop.toString());
            intervalsToProcess.add(currentInverval);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ApiRequests apiRequests = new ApiRequests();
        double minRate = 2147483647.0;
        double maxRate = -2147483648.0;

        // Fetch gold data
        for (ArrayList<String> interval : intervalsToProcess) {
            String currentDateStart = interval.get(0);
            String currentDateStop = interval.get(1);
            String currentUrl = String.format("http://api.nbp.pl/api/cenyzlota/%s/%s/", currentDateStart, currentDateStop);
            try {
                JSONArray curCurrencyJSON = apiRequests.readJsonArrayFromUrl(currentUrl);
//                System.out.println(curCurrencyJSON);
//                JSONArray rates = (JSONArray) curCurrencyJSON.get("rates");
//                System.out.println(curCurrencyJSON);
                for (Object obj : curCurrencyJSON) {
                    Double rate = ((BigDecimal) ((JSONObject) obj).get("cena")).doubleValue();
                    String date = (String) ((JSONObject) obj).get("data");
                    dataset.addValue(rate, "Gold", date);
                    if (rate > maxRate) {
                        maxRate = rate;
                    }
                    if (rate < minRate) {
                        minRate = rate;
                    }
                }
                this.CurrencyMinRate = minRate;
                this.CurrencyMaxRate = maxRate;
                this.dataset = dataset;
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Błąd przy pobieraniu danych");
            }
        }

//        // Fetch US Dollar Index data
//        for (ArrayList<String> interval : intervalsToProcess) {
//            String currentDateStart = interval.get(0);
//            String currentDateStop = interval.get(1);
//            String currentUrl = String.format("http://api.nbp.pl/api/exchangerates/rates/a/usd/%s/%s/", currentDateStart, currentDateStop);
//            try {
//                JSONObject curCurrencyJSON = apiRequests.readJsonObjectFromUrl(currentUrl);
//                System.out.println(curCurrencyJSON);
//                JSONArray rates = (JSONArray) curCurrencyJSON.get("rates");
//                System.out.println(rates);
//                for (Object obj : rates) {
//                    Double rate = ((BigDecimal) ((JSONObject) obj).get("mid")).doubleValue();
//                    String date = (String) ((JSONObject) obj).get("effectiveDate");
//                    ArrayList<Object> usDollarIndexValueRow = getUsDollarIndex(date, rate);
//                    dataset.addValue((double)usDollarIndexValueRow.get(0), (String)usDollarIndexValueRow.get(1), date);
//                }
//                System.out.println(dataset);
//                this.dataset = dataset;
//            } catch (Exception e) {
//                System.out.println(e);
//                System.out.println("Błąd przy pobieraniu danych");
//            }
//        }
    }

//    public ArrayList<Object> getUsDollarIndex(String date, double USDRate) {
//
//        ArrayList<Object> rowToPlot = new ArrayList<>();
//        ApiRequests apiRequests = new ApiRequests();
//        Map<String, Double> componentCurrencies = new HashMap<>();
//        componentCurrencies.put("USD", USDRate);
//        String[] usIndexCurrencies = {"EUR", "JPY", "GBP", "CAD", "SEK", "CHF"};
//        for (String currentCurrency : usIndexCurrencies) {
//            String currentUrl = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/", currentCurrency, date);
//            try {
//                JSONObject curCurrencyJSON = apiRequests.readJsonObjectFromUrl(currentUrl);
//                System.out.println(curCurrencyJSON);
//                JSONArray rates = (JSONArray) curCurrencyJSON.get("rates");
//                JSONObject ratesObj = (JSONObject) rates.get(0);
//                System.out.println(rates);
//                Double rate = ((BigDecimal) ratesObj.get("mid")).doubleValue();
//                componentCurrencies.put(currentCurrency, rate);
//            } catch (Exception e) {
//                System.out.println(e);
//                System.out.println("Błąd przy pobieraniu danych");
//            }
//        }
//        double usDollarIndexValue = 50.14348112;
//        usDollarIndexValue = usDollarIndexValue
//                * (componentCurrencies.get("EUR") * (Math.pow(componentCurrencies.get("USD"), -0.576)))
//                * (componentCurrencies.get("USD") * (Math.pow(componentCurrencies.get("JPY"), 0.136)))
//                * (componentCurrencies.get("GBP") * (Math.pow(componentCurrencies.get("USD"), -0.119)))
//                * (componentCurrencies.get("USD") * (Math.pow(componentCurrencies.get("CAD"), 0.091)))
//                * (componentCurrencies.get("USD") * (Math.pow(componentCurrencies.get("SEK"), 0.042)))
//                * (componentCurrencies.get("USD") * (Math.pow(componentCurrencies.get("CHF"), 0.036)));
//        System.out.println(usDollarIndexValue);
//        rowToPlot.add(usDollarIndexValue);
//        rowToPlot.add("US Dollar Index");
//
//        return rowToPlot;
//    }

}
