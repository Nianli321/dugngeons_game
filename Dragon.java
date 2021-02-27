package unsw.dungeon;

public class Dragon extends Enemy {

    public Dragon(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
    }

    /**
     * move direction override, so that dragon can move
     * over all obstacles
     * @param x coordinate
     * @param y coordinate
     */
    @Override
    public void moveDirection(int x, int y) {
        x().set(x);
        y().set(y);
    }
    
}