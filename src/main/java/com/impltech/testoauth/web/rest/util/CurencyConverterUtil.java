package com.impltech.testoauth.web.rest.util;

import com.impltech.testoauth.Enum.Currency;
import com.impltech.testoauth.config.CurrencyConfig;

import java.util.*;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
public class CurencyConverterUtil {

    private CurrencyConfig.CurrencyRatesConfig currencyRatesConfig;

    public CurencyConverterUtil(CurrencyConfig.CurrencyRatesConfig currencyRatesConfig) {
        this.currencyRatesConfig = currencyRatesConfig;
    }

    public Double convert(Double amount, Currency convertFrom, Currency convertTo) {
        Double result = 0.0;
        final Map<Currency, List<Double>> currentRates = getCourseRate(convertFrom);
        if (convertFrom.getTypeValue().equals("uah")) {
            if (convertTo.getTypeValue().equals("usd")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else if (convertFrom.getTypeValue().equals("usd")) {
            if (convertTo.getTypeValue().equals("uah")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else if (convertFrom.getTypeValue().equals("eur")) {
            if (convertTo.getTypeValue().equals("uah")) {
                result = amount * currentRates.get(convertFrom).get(0);
            } else {
                result = amount * currentRates.get(convertFrom).get(1);
            }
        } else {
        }
        return result;
    }

    private Map<Currency, List<Double>> getCourseRate(Currency currency) {
        Double uahCourseRate;
        Double usdCourseRate;
        Double eurCourseRate;
        List<Double> fromCurrency = new ArrayList<>();
        switch (currency) {
            case UAH:
                CurrencyConfig.CurrencyRatesConfig.Uah uahRate = currencyRatesConfig.getUah();
                usdCourseRate = uahRate.getUsd();
                eurCourseRate = uahRate.getEur();
                fromCurrency.addAll(Arrays.asList(usdCourseRate, eurCourseRate));
                break;
            case USD:
                CurrencyConfig.CurrencyRatesConfig.Usd usdRate = currencyRatesConfig.getUsd();
                uahCourseRate = usdRate.getUah();
                eurCourseRate = usdRate.getEur();
                fromCurrency.addAll(Arrays.asList(uahCourseRate, eurCourseRate));
                break;
            case EUR:
                CurrencyConfig.CurrencyRatesConfig.Eur eurRate = currencyRatesConfig.getEur();
                uahCourseRate = eurRate.getUah();
                usdCourseRate = eurRate.getUsd();
                fromCurrency.addAll(Arrays.asList(uahCourseRate, usdCourseRate));
                break;
        }
        return new HashMap<Currency, List<Double>>() {{ put(currency, fromCurrency); }};
    }
}
