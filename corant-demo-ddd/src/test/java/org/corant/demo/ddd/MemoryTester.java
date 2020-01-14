package org.corant.demo.ddd;

import org.corant.Corant;
import org.corant.demo.ddd.application.Memorys;
import org.corant.kernel.util.Instances;

public class MemoryTester {

	public static void main(String...strings) throws Exception {
		try {
			System.out.println("we start!"+Thread.currentThread().getId());
			Thread.sleep(20000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try(Corant corant = Corant.run(MemoryTester.class)){
			try {
				System.out.println("we start!"+Thread.currentThread().getId());
				Thread.sleep(20000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Instances.resolve(Memorys.class).get().test();
			//Executor e = Executors.newFixedThreadPool(3);
			//e.execute(()->Instances.resolve(Memorys.class).get().test());
			//e.execute(()->Instances.resolve(Memorys.class).get().test());
			//e.execute(()->Instances.resolve(Memorys.class).get().test());
			try {
				System.out.println("we stop!"+Thread.currentThread().getId());
				Thread.sleep(10000000000000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
