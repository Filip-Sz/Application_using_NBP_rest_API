package api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.Date;

public class CurrencyFull {

    public String code;
    public double buyValue;
    public double sellValue;
    public String currency;
    public String effectiveDate;

    @Override
    public String toString() {
        return "CurrencyFull{" +
                "code='" + code + '\'' +
                ", buyValue=" + buyValue +
                ", sellValue=" + sellValue +
                ", currency='" + currency + '\'' +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
