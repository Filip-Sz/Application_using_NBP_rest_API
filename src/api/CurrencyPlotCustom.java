package api;

import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CurrencyPlotCustom {

    public DefaultCategoryDataset dataset;
    public double CurrencyMinRate;
    public double CurrencyMaxRate;
    private String[] chosenCurrencies;
    private String dateStart;
    private String dateStop;



    public CurrencyPlotCustom(String[] chosenCurrencies, String dateStart, String dateStop) {
        this.chosenCurrencies = chosenCurrencies;
        this.dateStart = dateStart;
        this.dateStop = dateStop;

        ArrayList<ArrayList<String>> intervalsToProcess = new ArrayList<ArrayList<String>>();
        LocalDate DateDateStart = LocalDate.parse(dateStart);
        LocalDate DateDateStop = LocalDate.parse(dateStop);

        if (DateDateStop.isBefore(DateDateStart)){
            this.dateStart = dateStop;
            this.dateStop = dateStart;

            LocalDate tempDate = DateDateStart;
            DateDateStart = DateDateStop;
            DateDateStop = tempDate;
        }

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
        } else {
            ArrayList<String> currentInverval = new ArrayList<String>();
            currentInverval.add(DateDateStart.toString());
            currentInverval.add(DateDateStop.toString());
            intervalsToProcess.add(currentInverval);

        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ApiRequests apiRequests = new ApiRequests();
        double minRate = 2147483647.0;
        double maxRate = -2147483648.0;
        for (String currentCurrency : chosenCurrencies) {
            if (!Objects.equals(currentCurrency, "")) {
                for (ArrayList<String> interval : intervalsToProcess) {
                    String currentDateStart = interval.get(0);
                    String currentDateStop = interval.get(1);
                    String currentUrl = String.format("http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/%s/", currentCurrency, currentDateStart, currentDateStop);
                    try {
                        JSONObject curCurrencyJSON = apiRequests.readJsonObjectFromUrl(currentUrl);
//                        System.out.println(curCurrencyJSON);
                        JSONArray rates = (JSONArray) curCurrencyJSON.get("rates");
//                        System.out.println(rates);
                        for (Object obj : rates) {
                            Double rate = ((BigDecimal) ((JSONObject) obj).get("mid")).doubleValue();
                            String date = (String) ((JSONObject) obj).get("effectiveDate");

                            if (rate > maxRate) {
                                maxRate = rate;
                            }
                            if (rate < minRate) {
                                minRate = rate;
                            }
                            dataset.addValue(rate, currentCurrency, date);
                        }
//                        System.out.println(dataset);
                        this.CurrencyMinRate = minRate;
                        this.CurrencyMaxRate = maxRate;
                        this.dataset = dataset;
                    } catch (Exception e) {
                        System.out.println(e);
                        System.out.println("Błąd przy pobieraniu danych");
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        String[] testcodes = {"eur"};
        CurrencyPlotCustom test = new CurrencyPlotCustom(testcodes, "2021-12-01", "2022-01-01");
        ActualityRange test2 = ActualityRange.Daily;
        System.out.println(test);
    }
}
