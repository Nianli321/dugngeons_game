package unsw.dungeon;

public interface Goal {
    public Boolean checkGoal(Dungeon dungeon, Player player);
    public String getGoal();
}