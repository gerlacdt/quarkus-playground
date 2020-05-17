package org.acme.restclient;

import java.util.ArrayList;
import java.util.List;

public class Country {

    public String name;
    public String alpha2Code;
    public String capital;
    public String region;
    public int population;
    public List<Double> latlng = new ArrayList<>();
    public List<String> timezones = new ArrayList<>();
    public List<Currency> currencies;

    public static class Currency {
        public String code;
        public String name;
        public String symbol;
    }
}
