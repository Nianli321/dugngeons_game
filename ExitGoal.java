package unsw.dungeon;

public class ExitGoal implements Goal{
    
    public String goal;

    /**
     * constructor
     * set goal to exit
     */
    public ExitGoal() {
        goal = "Get to the exit";
    }

    /**
     * override checkgoal
     */
    @Override
    public Boolean checkGoal(Dungeon dungeon, Player player) {
        // if stood on exit, then goal complete
        Exit e = dungeon.getExit();
        if (player.getX() == e.getX() && player.getY() == e.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public String getGoal() {
        return goal;
    }
    
}