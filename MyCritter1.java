package assignment4;

import java.util.*;

public class MyCritter1 extends Critter.TestCritter {

	public MyCritter1(){
		super();
		System.out.println("CreateMyCritter1");
	}

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}
