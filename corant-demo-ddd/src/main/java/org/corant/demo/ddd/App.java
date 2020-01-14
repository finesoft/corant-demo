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

	public static void main(String... args) {
		System.setProperty("corant.config.profile", "me");
		Corant.run(App.class, args);
	}

}
