package org.corant.demo.ddd;

import java.time.Instant;
import org.corant.shared.normal.Names.ConfigNames;

public class Development {

  public static void main(String... args) {
    System.setProperty(ConfigNames.CFG_PROFILE_KEY, "me");
    // HibernateOrmDeveloperKits.stdoutUpdateSchema("sql");
    System.out.println(Instant.ofEpochMilli(1579588754757L).toString());
  }

}
