package unsw.dungeon;

public class TreasureGoal implements Goal {
    
    public String goal;

    /**
     * constructor
     * set goal to exit
     */
    public TreasureGoal() {
        goal = "Collect Treasure";
    }
    
    @Override
    public Boolean checkGoal(Dungeon dungeon, Player player) {
        return dungeon.goalTreasure();
    }

    @Override
    public String getGoal() {
        return goal;
    }
}