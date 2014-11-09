package com.karateca.protractor;

import com.intellij.openapi.util.Pair;
import junit.framework.TestCase;

public class LocatorTesterTest extends TestCase {

//  Pair<String, String> testElementExplorerResponse(final String response) {
//    return new LocatorTester(new JsonReader() {
//      @Override
//      public String read(String locator) {
//        return response;
//      }
//    }).testLocator("");
//  }
//
//  public void testResultWithCountValue() {
//    String response = "{\"results\":{\"element.all(by.model('yourName')).count()\":1}}";
//    Pair<String, String> pair = testElementExplorerResponse(response);
//
//    assertEquals("element.all(by.model('yourName')).count()", pair.first);
//    assertEquals("1", pair.second);
//  }
//
//  public void testEmptyStringResult() {
//    String response = "{\"results\":{\"element(by.model('yourName'))." +
//        "getAttribute('value')\":\"\"}}";
//    Pair<String, String> pair = testElementExplorerResponse(response);
//
//    assertEquals("element(by.model('yourName')).getAttribute('value')", pair.first);
//    assertEquals("", pair.second);
//  }
//
//  public void testResultIsWebElement(){
//    String response = "{\"results\": {\"element(by.model('yourName'))\": " +
//        "{\"locator_\": {}, \"parentElementFinder_\": null}}}";
//    Pair<String, String> pair = testElementExplorerResponse(response);
//
//    assertEquals("element(by.model('yourName'))", pair.first);
//    assertEquals("{\"locator_\": {}, \"parentElementFinder_\": null}", pair.second);
//  }
}
