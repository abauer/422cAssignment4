/* CRITTERS Spider.java
 * EE422C Project 4 submission by
 * Anthony Bauer
 * amb6869
 * 16480
 * Grant Uy
 * gau84
 * 61480
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4;

/**
 * Spider Critter
 * The Spider Critter will never move backwards
 * Upon reproducing the Spider produces two offspring and then kills itself
 */
public class Spider extends Critter {

    @Override
    public String toString() { return "S"; }

    private int dir;

    public Spider() {
        dir = Critter.getRandomInt(4);
    }

    public boolean fight(String not_used) {
        run(dir);
        dir = Critter.getRandomInt(4);
        return true;
    }

    @Override
    public void doTimeStep() {
        walk(dir);
        dir = Critter.getRandomInt(4);
        if (getEnergy() > Params.start_energy+1) {
            reproduce(new Spider(), Critter.getRandomInt(8));
            reproduce(new Spider(), Critter.getRandomInt(8));
            while(getEnergy()>0)
                walk(dir);
        }
    }
}
