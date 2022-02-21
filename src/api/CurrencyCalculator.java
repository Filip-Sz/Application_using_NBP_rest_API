package api;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class CurrencyCalculator {

    private Option option;
    private String currencyLeftCode;
    private String currencyRightCode;
    private double moneyInputValue;

    public double currencyLeftVal;
    public LocalDate leftDate;
    public double currencyRightVal;
    public LocalDate rightDate;

    public double currencyLeftRightVal;
    public double finalValue;

    public CurrencyCalculator(Option option, String currencyLeftCode,
                              String currencyRightCode, double moneyInputValue) throws IOException {
        this.option = option;
        this.currencyLeftCode = currencyLeftCode;
        this.currencyRightCode = currencyRightCode;
        this.moneyInputValue = moneyInputValue;
        getRates();
        this.currencyLeftRightVal = round(currencyLeftVal / currencyRightVal, 4);
        this.finalValue = round(currencyLeftRightVal * moneyInputValue, 4);
    }

    private void getRates() throws IOException {
        ApiRequests requests = new ApiRequests();
        CurrencyFull leftCur = requests.getSingleFullCurreny(currencyLeftCode);
        this.leftDate = LocalDate.parse(leftCur.effectiveDate);
        CurrencyFull rightCur = requests.getSingleFullCurreny(currencyRightCode);
        this.rightDate = LocalDate.parse(rightCur.effectiveDate);

        if (option == Option.buy) {
            this.currencyLeftVal = leftCur.buyValue;
            this.currencyRightVal = rightCur.buyValue;
        } else {
            this.currencyLeftVal = leftCur.sellValue;
            this.currencyRightVal = rightCur.sellValue;
        }

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        return "CurrencyCalculator{" +
                "option=" + option +
                ", currencyLeftCode='" + currencyLeftCode + '\'' +
                ", currencyRightCode='" + currencyRightCode + '\'' +
                ", moneyInputValue=" + moneyInputValue +
                ", \n currencyLeftVal=" + currencyLeftVal +
                ", leftDate=" + leftDate +
                ", currencyRightVal=" + currencyRightVal +
                ", rightDate=" + rightDate +
                ", \n currencyLeftRightVal=" + currencyLeftRightVal +
                ", finalValue=" + finalValue +
                '}';
    }
}
