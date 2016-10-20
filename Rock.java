package assignment4;

public class Rock extends Critter {

    @Override
    public String toString() { return "R"; }

    public boolean fight(String not_used) { return true; }

    @Override
    private int rollFight(boolean not_used){
        return Integer.MAX_VALUE;
    }

    @Override
    public void doTimeStep() {
        if (getEnergy() > 150) {
            Rock child = new Rock();
            reproduce(child, 0);
        }
    }
}
