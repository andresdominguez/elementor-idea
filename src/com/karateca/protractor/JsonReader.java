package com.karateca.protractor;

import java.io.IOException;

public interface JsonReader {
  String read(String locator) throws IOException;
}
