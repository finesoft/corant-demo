package org.corant.demo.ddd;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.corant.Corant;

@ApplicationPath("/ddd")
public class App extends Application {

  public App() {}

  public static void main(String... args) throws Exception {
    Corant.run(App.class, args);
  }

}
