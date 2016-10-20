package assignment4;

/*
 * Example critter
 */
public class SquareCritter extends Critter {
    private int dir;
    private int sideLength;
    private int stepsLeft;

    public SquareCritter() {
        dir = 0;
        sideLength = Critter.getRandomInt(Params.world_width-1)+2; // 2-6
        stepsLeft = sideLength-1;
    }

    @Override
    public String toString() { return "#"; }

    public boolean fight(String opponent) {
        return opponent.equals("@");
    }

    @Override
    public void doTimeStep() {
        walk(dir);
        if (--stepsLeft == 0) {
            dir = (dir+2)%8;
            stepsLeft = sideLength-1;
        }
        if (Critter.getRandomInt(4) == 1 && getEnergy() > Params.start_energy/2) {
            reproduce(new SquareCritter(), Critter.getRandomInt(8));
        }
    }
}
