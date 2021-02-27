package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

public class ComplexGoal implements Goal{
    String condition;
    List<Goal> goalsList;

    /**
     * Constructor
     * @param condition OR, AND
     */
    public ComplexGoal(String condition) {
        this.condition = condition;
        this.goalsList = new ArrayList<Goal>();
    }

    /**
     * add goal to list of goals
     * @param goal
     */
    public void addGoal(Goal goal) {
        goalsList.add(goal);
    }

    public List<Goal> getSubGoals() {
        return goalsList;
    }

    /**
     * function that checks if goals are complete
     * @param dungeon current dungeon
     * @param player current player
     * @return Boolean true if goal is complete, false if not
     */
    @Override
    public Boolean checkGoal(Dungeon dungeon, Player player) {
        if (condition == "AND") {
            // in the AND condition, cycle through all goals
            for (Goal g: goalsList) {
                // if any of the goals are incomplete, return false
                if (!g.checkGoal(dungeon, player)) return false;
            }
            // if none of the goals are incomplete, return true
            return true;
        } else if (condition == "OR") {
            // in the OR condition, cycle through all goals
            for (Goal g: goalsList) {
                // if any of the goals are complete, return true
                if (g.checkGoal(dungeon, player)) return true;
            }
            // if no goals are complete, return false
            return false;
        }
        return false;
    }

    @Override
    public String getGoal() {
        return condition;
    }
}