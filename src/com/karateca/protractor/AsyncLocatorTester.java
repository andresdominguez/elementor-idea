package com.karateca.protractor;

import com.intellij.openapi.util.Pair;
import com.intellij.util.EventDispatcher;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class AsyncLocatorTester {
  private final JsonReader jsonReader;
  private static final String KEY_PATTERN = "\"?(.*?)\"?";
  private static final String VALUE_PATTERN = "(^\\s*\"?)(.*?)(\\s*\"?$)";
  private static final String RESULTS_PATTERN = "(\\{\"results\":\\s*\\{)(.+)(}})";
  private final EventDispatcher<ChangeListener> myEventDispatcher =
      EventDispatcher.create(ChangeListener.class);

  public AsyncLocatorTester(JsonReader jsonReader) {
    this.jsonReader = jsonReader;
  }

  public void testLocator(final String locator) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Pair<String, String> pair = submitRequest(locator);
        myEventDispatcher.getMulticaster().stateChanged(new ChangeEvent(pair));
      }
    });
  }

  /**
   * Register for change events.
   *
   * @param changeListener The listener to be added.
   */
  public void addResultsReadyListener(ChangeListener changeListener) {
    myEventDispatcher.addListener(changeListener);
  }

  private Pair<String, String> submitRequest(String locator) {
    String json;
    try {
      json = jsonReader.read(locator);
    } catch (IOException e) {
      return new Pair<String, String>(
          null,
          "Error testing locator: " + e.getMessage()
      );
    }

    // Parse the json string. It looks like this:
    // {"results":{"element.all(by.model('yourName')).count()":1}}
    String keyAndValue = json.replaceAll(RESULTS_PATTERN, "$2");

    // The key / value looks like this. Split at ":
    // "element.all(by.model('yourName')).count()":1
    int i = keyAndValue.indexOf("\":") + 1;
    if (i == 0) {
      return null;
    }

    String keySubstring = keyAndValue.substring(0, i);
    String valueSubstring = keyAndValue.substring(i + 1);

    return new Pair<String, String>(
        keySubstring.replaceAll(KEY_PATTERN, "$1"),
        valueSubstring.replaceAll(VALUE_PATTERN, "$2")
    );
  }
}
