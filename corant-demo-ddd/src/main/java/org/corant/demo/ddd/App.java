package org.corant.demo.ddd;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.corant.Corant;
import org.eclipse.microprofile.auth.LoginConfig;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("/ddd")
public class App extends Application {

	public App() {
	}

	public static void main(String... args) throws Exception {
		System.setProperty("corant.config.profile", "dev");
//		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
//		System.setProperty("log4j.configurationFile", "META-INF/log4j2.xml");
		Corant.run(App.class, args);
	}

}
