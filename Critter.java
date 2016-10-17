/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4;


import com.intellij.openapi.graph.util.HashMap2D;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.util.*;
import java.util.stream.Collectors;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new ArrayList<Critter>();
	private static List<Critter> babies = new ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	private final void moveInDirection(int direction, int distance) {
		// right
		if (direction == 7 || direction == 0 || direction == 1)
			x_coord = (x_coord+distance)%Params.world_width;
		// up
		if (direction == 1 || direction == 2 || direction == 3)
			y_coord = (y_coord+distance)%Params.world_height;
		// down
		if (direction == 3 || direction == 4 || direction == 5)
			x_coord = (x_coord+distance)%Params.world_width;
		// left
		if (direction == 1 || direction == 2 || direction == 3)
			y_coord = (y_coord+distance)%Params.world_height;
	}

	protected final void walk(int direction) {
		energy -= Params.walk_energy_cost;
		moveInDirection(direction, 1);
	}
	
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
		moveInDirection(direction, 2);
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.min_reproduce_energy) return;
		offspring.energy = energy/2;
		energy -= energy/2;
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		offspring.moveInDirection(direction, 1);
		babies.add(offspring);
	}

	protected int rollFight(boolean fight){
		return fight ? getRandomInt(energy) : 0;
	}

	protected boolean cull(){
		energy-=Params.rest_energy_cost;
		if(energy<=0)
			return true;
		return false;
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try {
			population.add((Critter)Class.forName(critter_class_name).newInstance());
		} catch(Exception e){
			throw new InvalidCritterException("Could not find Critter of type "+critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result;
		try {
			Class c = Class.forName(critter_class_name);
			result = population.stream().filter(c::isInstance).collect(Collectors.toList());
		} catch(Exception e){
			throw new InvalidCritterException("Could not find Critter of type "+critter_class_name);
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population = new ArrayList<>();
	}
	
	public static void worldTimeStep() {
		population.forEach(Critter::doTimeStep);	//do time step

		Set<Integer> locations = new HashSet<>();
		HashMap<Integer,LinkedList<Critter>> crits = new HashMap<>();
		Set<Integer> collision = new HashSet<>();
		int w = Params.world_width;
		int h = Params.world_height;
		for(Critter c : population) {	//identify collisions
			int sum = w>h ? c.x_coord+c.y_coord*w : c.y_coord+c.x_coord*h;
			if (!crits.containsKey(sum)) crits.put(sum,new LinkedList<>());
			crits.get(sum).add(c);
			if(!locations.contains(sum))
				locations.add(sum);
			else {
				collision.add(sum);
			}
		}
		for(Integer i : collision){		//handle collisions
			LinkedList<Critter> result = crits.get(i);
			int origx = result.peek().x_coord; int origy = result.peek().y_coord;
			while(!result.isEmpty()){
				Critter A = result.poll();
				Critter B = result.poll();
				boolean afight = A.fight(B.toString());
				boolean bfight = B.fight(A.toString());
				boolean aflag = false;
				boolean bflag = false;
				if(A.x_coord!=origx||A.y_coord!=origx) {	//A is not in spot we cannot add A & B defaults
					aflag = true;
				}
				if(B.x_coord!=origx||B.y_coord!=origx) {	//B is not in spot we cannot add B & A defaults
					bflag = true;
				}
				if(A.energy<=0) {
					population.remove(A);	//A dies so we cannot add A & B defaults
					aflag = true;
				}
				if(B.energy<=0) {
					population.remove(B);	//B dies so we cannot add B & A defaults
					bflag = true;
				}
				if(!bflag && aflag) {	//A moved or Died and B is still there
					result.addFirst(B);
					continue;
				}
				if(!aflag && bflag) {	//B moved or Died and B is still there
					result.addFirst(A);
					continue;
				}
				if(aflag&&bflag){	//both A and B moved or Died
					continue;
				}
				int aroll = A.rollFight(afight);
				int broll = B.rollFight(bfight);
				if(aroll>=broll){	//A wins tiebreaker
					population.remove(B);
					A.energy+=B.energy/2;
					result.addFirst(A);
				}
			}
		}

		babies.stream().forEach(population::add);	//handle reproduce

		Iterator<Critter> it = population.iterator();
		while(it.hasNext()){
			Critter c = it.next();
			if(c.cull()){
				it.remove();
			}
		}
	}
	
	public static void displayWorld() {
		String border = "+"
				+ Collections.nCopies(Params.world_width,"-").stream().collect(Collectors.joining())
				+ "+";
		System.out.println(border);
		System.out.println(border);
	}
}
