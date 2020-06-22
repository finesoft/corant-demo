package org.corant.demo.ddd;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.corant.kernel.boot.DirectRunner;

@ApplicationPath("/ddd")
public class App extends Application {

  public App() {}

  public static void main(String... args) {
    System.setProperty("corant.config.profile", "me");
    // HibernateOrmDeveloperKits.stdoutUpdateSchema("sql");
    DirectRunner.main(args);
  }

}
