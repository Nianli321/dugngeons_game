package unsw.dungeon;

public class SwitchGoal implements Goal{
    
    public String goal;

    /**
     * constructor
     * set goal to exit
     */
    public SwitchGoal() {
        goal = "Press all switches";
    }

    /**
     * override to check if switches are pressed
     */
    @Override
    public Boolean checkGoal(Dungeon dungeon, Player player) {
        return dungeon.goalSwitches();
    }

    @Override
    public String getGoal() {
        return goal;
    }
}