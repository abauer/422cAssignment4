package assignment4;

/*
 * Example critter
 */
public class Spider extends Critter {

    @Override
    public String toString() { return "S"; }

    private int dir;

    public Spider() {
        dir = Critter.getRandomInt(8);
    }

    public boolean fight(String not_used) { run(dir); return true; }

    @Override
    public void doTimeStep() {
		/* take one step forward */
        walk(dir);
        if (getEnergy() > 150) {
            reproduce(new Spider(), Critter.getRandomInt(8));
            reproduce(new Spider(), Critter.getRandomInt(8));
            while(getEnergy()>0)
                walk(dir);
        }
    }
}
