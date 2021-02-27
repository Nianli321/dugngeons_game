package unsw.dungeon;

public class EnemyGoal implements Goal{
    
    public String goal;

    /**
     * constructor
     * set goal to exit
     */
    public EnemyGoal() {
        goal = "Kill all enemies";
    }

    /**
     * override to check if all enemies are killed
     */
    @Override
    public Boolean checkGoal(Dungeon dungeon, Player player) {
        //if no enemies left on the map
        return dungeon.goalEnemies();
    }

    @Override
    public String getGoal() {
        return goal;
    }
}