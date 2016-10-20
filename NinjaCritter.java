package assignment4;

/*
 * Example critter
 */
public class NinjaCritter extends Critter {

    @Override
    public String toString() { return " "; }

    public boolean fight(String not_used) {
        return true;
    }

    @Override
    public void doTimeStep() {
		// always moves upward
        walk(Critter.getRandomInt(3)+1);
    }
}
