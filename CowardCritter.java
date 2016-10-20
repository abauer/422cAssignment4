package assignment4;

/*
 * Example critter
 */
public class CowardCritter extends Critter {

    @Override
    public String toString() { return "\u00A9"; }

    private int dir;

    public CowardCritter() {
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) {
        reproduce(new CowardCritter(), Critter.getRandomInt(8));
        return true;
    }

    @Override
    public void doTimeStep() {
		/* take one step forward */
        walk(dir);
        if (getEnergy() > 150) {
            reproduce(new CowardCritter(), Critter.getRandomInt(8));
        }
    }
}
