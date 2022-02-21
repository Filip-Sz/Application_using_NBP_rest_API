package api;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ActualityHandler {

    private final String[] codes = {"eur", "usd", "chf", "gbp"};
    public List<ActualityCurrency> CurrenciesList;
    private ActualityRange range;


    public ActualityHandler(ActualityRange range) {
        try {
            LocalDate staticPreviousDate;
            this.range = range;

            ApiRequests requests = new ApiRequests();

            List<ActualityCurrency> localCurrencyList = new ArrayList<>();
            LocalDate previousDate;
            LocalDate todayDate = LocalDate.now();

            if (range == ActualityRange.Daily) {
                previousDate = todayDate.minusDays(1);
            } else if (range == ActualityRange.Weekly) {
                previousDate = todayDate.minusWeeks(1);
            } else {
                previousDate = todayDate.minusMonths(1);
            }
            staticPreviousDate = previousDate;

            ActualityCurrency previousCur = new ActualityCurrency();
            ActualityCurrency todayCur = new ActualityCurrency();

            for (String currentCode : codes) {
                boolean missingData = false;
                previousDate = staticPreviousDate;
                try {
                    todayCur = requests.getSingleCurrenySpecificDate(currentCode, todayDate.toString(), false);
                } catch (FileNotFoundException e) {
                    todayCur = requests.getSingleCurrenySpecificDate(currentCode, todayDate.toString(), true);
                    missingData = true;
                } catch (Exception e) {
                    System.out.println("Mozliwy problem z internetem");
                }
                if (missingData | Objects.equals(todayDate.getDayOfWeek().toString(), "MONDAY")) {
                    previousDate = previousDate.minusDays(2);
                }

                try {
                    previousCur = requests.getSingleCurrenySpecificDate(currentCode, previousDate.toString(), false);
                } catch (FileNotFoundException e) {
                    try {
                        previousDate = previousDate.minusDays(1);
                        previousCur = requests.getSingleCurrenySpecificDate(currentCode, previousDate.toString(), false);

                    } catch (Exception ignored) {
                    }
                } catch (Exception e) {
                    System.out.println("Mozliwy problem z internetem");
                }



                ActualityCurrency changesCur = new ActualityCurrency();
                changesCur.name = currentCode.toUpperCase(Locale.ROOT);
                changesCur.average = round(((todayCur.average - previousCur.average) / previousCur.average) * 100, 2);
                changesCur.selling = round(((todayCur.selling - previousCur.selling) / previousCur.selling) * 100, 2);
                changesCur.buying = round(((todayCur.buying - previousCur.buying) / previousCur.buying) * 100, 2);
                localCurrencyList.add(changesCur);
            }

            this.CurrenciesList = localCurrencyList;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static void main(String[] args) {
        ActualityHandler test = new ActualityHandler(ActualityRange.Monthly);
        List<ActualityCurrency> testlist = test.CurrenciesList;

        for (ActualityCurrency cur : testlist) {
            System.out.println(cur);
        }

    }

    public List<ActualityCurrency> getCurrenciesList() {
        return CurrenciesList;
    }
}
