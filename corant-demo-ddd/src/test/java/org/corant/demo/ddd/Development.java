package org.corant.demo.ddd;

import org.corant.shared.normal.Names.ConfigNames;
import org.corant.suites.jpa.hibernate.HibernateOrmDeveloperKits;

public class Development {

  public static void main(String... args) {
    System.setProperty(ConfigNames.CFG_PROFILE_KEY, "me");
    HibernateOrmDeveloperKits.stdoutUpdateSchema("sql");
  }

}
