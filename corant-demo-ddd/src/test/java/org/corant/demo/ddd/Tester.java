package org.corant.demo.ddd;

import org.corant.devops.test.unit.CorantJUnit4ClassRunner;
import org.corant.devops.test.unit.RunConfig;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CorantJUnit4ClassRunner.class)
@RunConfig(configClass = App.class,profile = "dev",randomWebPort = true)
public class Tester {

	@Test
	public void hello() {
		
	}
}
