package api;

import java.io.IOException;

public class Tests {
    public static void main(String[] args) throws IOException {
        String codeleft = "eur";
        String codeRight = "czk";
        double value = 20.0;
        CurrencyCalculator result = new CurrencyCalculator(Option.buy,codeleft, codeRight, value);
        System.out.println(result);
        CurrencyCalculator result2 = new CurrencyCalculator(Option.sell,codeleft, codeRight, value);
        System.out.println(result2);
    }
}
