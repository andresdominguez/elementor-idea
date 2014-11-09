package com.karateca.protractor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ElementExplorerReader implements JsonReader {

  private static final String baseUrl = "http://localhost:13000/testSelector?popupInput=";

  @Override
  public String read(String locator) throws IOException {
    StringBuilder sb = new StringBuilder();

    String url = baseUrl + URLEncoder.encode(locator, "UTF-8");
    URLConnection connection = new URL(url).openConnection();
    BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream())
    );

    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      sb.append(inputLine);
    }
    in.close();

    return sb.toString();
  }
}
