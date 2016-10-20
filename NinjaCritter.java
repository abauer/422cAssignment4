/* CRITTERS NinjaCritter.java
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
