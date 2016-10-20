/* CRITTERS CowardCritter.java
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
 * CowardCritter
 * The CowardCritter will always move foward
 * Upon entering a fight the CowardCritter will preserve half its energy in the form of an offspring
 */
public class CowardCritter extends Critter {

    @Override
    public String toString() { return "&"; }

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
        walk(dir);
        if (getEnergy() > Params.start_energy+1) {
            reproduce(new CowardCritter(), Critter.getRandomInt(8));
        }
    }
}
