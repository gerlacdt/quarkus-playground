package org.acme.restclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class Country {

  @Nullable public String name;
  @Nullable public String alpha2Code;
  @Nullable public String capital;
  @Nullable public String region;
  @Nullable public int population;

  public List<Double> latlng = new ArrayList<>();
  public List<String> timezones = new ArrayList<>();

  @Nullable public List<Currency> currencies;

  public static class Currency {
    @Nullable public String code;
    @Nullable public String name;
    @Nullable public String symbol;
  }
}
