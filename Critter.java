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

import java.util.*;
import java.util.stream.Collectors;

public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new ArrayList<>();
	private static List<Critter> babies = new ArrayList<>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();

    /**
     * Gets a random number from a (possibly) seeded generator.
     * @param max the exclusive upper bound
     * @return a random number in the range [0,max)
     */
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

    /**
     * Resets the random number generator, seeding it with a given value.
     * @param new_seed the new seed value
     */
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	

    /**
     * Gets the string representation of a Critter.
     * @return a one-character long string that visually depicts your critter in the ASCII interface
     */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;

	private boolean hasMoved = false;

    /**
     * Internal method for moving a Critter a given number of tiles in a given direction
     * Only allows one movement per time step
     * @param direction integer in range [0,7] corresponding to a direction
     * @param distance number of squares to travel
     */
	private void moveInDirection(int direction, int distance) {
		if (hasMoved) return;
        hasMoved = true;
		// right
		if (direction == 7 || direction == 0 || direction == 1)
			x_coord = (x_coord+distance)%Params.world_width;
		// left
		else if (direction == 3 || direction == 4 || direction == 5)
			x_coord = (x_coord-distance)%Params.world_width;
		// up
		if (direction == 1 || direction == 2 || direction == 3)
			y_coord = (y_coord-distance)%Params.world_height;
		// down
		else if (direction == 5 || direction == 6 || direction == 7)
			y_coord = (y_coord+distance)%Params.world_height;
	}

    /**
     * Moves a Critter one tile in a given direction (at an energy cost)
     * @param direction integer in range [0,7] corresponding to a direction
     */
	protected final void walk(int direction) {
		energy -= Params.walk_energy_cost;
		moveInDirection(direction, 1);
	}

    /**
     * Moves a Critter two tiles in a given direction (at an energy cost)
     * @param direction integer in range [0,7] corresponding to a direction
     */
	protected final void run(int direction) {
		energy -= Params.run_energy_cost;
		moveInDirection(direction, 2);
	}

    /**
     * Initializes a new Critter one tile away from this Critter in a given direction
     * @param offspring newly-created Critter to initialize
     * @param direction integer in range [0,7] corresponding to a direction
     */
	protected final void reproduce(Critter offspring, int direction) {
		if (energy < Params.min_reproduce_energy) return;
		offspring.energy = energy/2; // round down
		energy -= energy/2; // round up
		// spawn offspring adjacent to parent
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		offspring.moveInDirection(direction, 1);
		babies.add(offspring);
	}

    /**
     * Abstract method for Critters' custom behavior during each world time step
     */
	public abstract void doTimeStep();

    /**
     * Checks whether a Critter wants to fight, giving it an opportunity to flee
     * @param opponent string representation of the opposing Critter
     * @return whether or not the Critter wants to fight
     */
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
			Critter c = (Critter)Class.forName(myPackage+"."+critter_class_name).newInstance();
			c.energy = Params.start_energy;
			c.x_coord = getRandomInt(Params.world_width);
			c.y_coord = getRandomInt(Params.world_height);
			population.add(c);
		} catch(Exception e) {
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
		try {
			Class c = Class.forName(myPackage+"."+critter_class_name);
			return population.stream().filter(c::isInstance).collect(Collectors.toList());
		} catch(Exception e) {
			throw new InvalidCritterException("Could not find Critter of type "+critter_class_name);
		}
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
		babies = new ArrayList<>();
	}

	private static int hashCoords(int x, int y) {
		int w = Params.world_width;
		int h = Params.world_height;
		return (w>h) ? x+y*w : y+x*h;
	}
	private static int unhashX(int hash) {
		int w = Params.world_width;
		int h = Params.world_height;
		return (w>h) ? hash%w : hash/h;
	}
	private static int unhashY(int hash) {
		int w = Params.world_width;
		int h = Params.world_height;
		return (w>h) ? hash/w : hash%h;
	}

	private static void updateHash(Critter c, HashMap<Integer,LinkedList<Critter>> hash){
		int aHash = hashCoords(c.x_coord, c.y_coord);
		if (!hash.containsKey(aHash))
			hash.put(aHash,new LinkedList<>());
		hash.get(aHash).add(c);
	}

	public static void worldTimeStep() {
		// do time step
		population.forEach(c -> {
			c.hasMoved = false;
			c.doTimeStep();
		});
		// pre-process locations
		Set<Integer> locations = new HashSet<>();
		HashMap<Integer,LinkedList<Critter>> crits = new HashMap<>();
		Set<Integer> collisions = new HashSet<>();
		for(Critter c : population) {	//identify collisions
			if(c.energy<=0) //Critter is dead, ignore it
				continue;
			int hash = hashCoords(c.x_coord, c.y_coord);
			// create list if necessary, then add critter
			if (!crits.containsKey(hash))
				crits.put(hash,new LinkedList<>());
			crits.get(hash).add(c);
			// check if seen, adding to collisions if necessary
			if (!locations.contains(hash))
				locations.add(hash);
			else
				collisions.add(hash);
		}
		// handle collisions
		for (Integer hash : collisions){
			LinkedList<Critter> result = crits.get(hash);
			int origx = unhashX(hash);
			int origy = unhashY(hash);
			while (result.size() > 1) {
				Critter A = result.poll();
				Critter B = result.poll();
				boolean aFlag = A.fight(B.toString());	//give critter option to move
				boolean bFlag = B.fight(A.toString());
				// check if either critter moved or died
				boolean aMoved = A.x_coord!=origx || A.y_coord!=origy;
				boolean aDied = A.energy <= 0;
				boolean bMoved = B.x_coord!=origx || B.y_coord!=origy;
				boolean bDied = B.energy <= 0;
				//determine if move was valid
				if (aMoved && crits.containsKey(hashCoords(A.x_coord,A.y_coord))){	//if space is already occupied, move back to conflict space
					A.x_coord = origx;
					A.y_coord = origy;
					aMoved = false;
				}
				if (bMoved && crits.containsKey(hashCoords(B.x_coord,B.y_coord))){	//if space is already occupied, move back to conflict space
					B.x_coord = origx;
					B.y_coord = origy;
					bMoved = false;
				}
				if(!(aMoved||aDied||bMoved||bDied)) {
					int aRoll = aFlag ? getRandomInt(A.getEnergy()+1) : 0;       //if we are still fighting, roll
					int bRoll = bFlag ? getRandomInt(B.getEnergy()+1) : 0;
                    if (aRoll >= bRoll) {    //A wins tiebreaker
						A.energy += B.energy / 2;
						B.energy = 0;
						bDied = true;
					} else {
						B.energy += A.energy / 2;
						A.energy = 0;
						aDied = true;
					}
				}
				if(!aDied)
					updateHash(A,crits);
				if(!bDied)
					updateHash(B,crits);
			}
		}
		// remove dead stuff
		Iterator<Critter> it = population.iterator();
		while (it.hasNext())
			if ((it.next().energy -= Params.rest_energy_cost) > 0) // removes rest cost
				it.remove();
		// add new Algae
		for(int i = 0; i<Params.refresh_algae_count; i++) {
			try{
				makeCritter("Algae");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		// handle reproduce
		population.addAll(babies);
		babies.clear();
	}
	
	public static void displayWorld() {
		// create +---+
		String border = "+"
				+ Collections.nCopies(Params.world_width,"-").stream().collect(Collectors.joining())
				+ "+";
		// upper border
		System.out.println(border);
		// pre-process all critters for simple lookups
		HashMap<Integer,String> critterIcons = new HashMap<>();
		for (Critter c : population)
			critterIcons.put(hashCoords(c.x_coord, c.y_coord), c.toString());
		// iterate over all locations
		for (int r=0; r<Params.world_height; r++) { //"y"
			System.out.print("|");
			for (int c=0; c<Params.world_width; c++) { //"x"
				int hash = hashCoords(c,r); // purposely "backwards"
				if (critterIcons.containsKey(hash)) {
					System.out.print(critterIcons.get(hash));
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("|\n");
		}
		// lower border
		System.out.println(border);
	}
}
