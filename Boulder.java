package unsw.dungeon;

public class Boulder extends Entity implements Obstacle {
    private Coordinate c;
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.c = new Coordinate(x, y);
    }

    /**
     * attempt to move the boulder in a given direction
     * directions up, down, left, right
     * @param direction
     * @return Boolean true if success, false if failure
     */
    public Boolean tryMove(String direction) {
        if (direction == "up") {
            if (!dungeon.occupied(this.getX(), this.getY()-1) && dungeon.getBoulder(this.getX(), this.getY() - 1) == null) {
                y().set(this.getY()-1);
                return true;
            }
        } else if (direction == "down") {
            if (!dungeon.occupied(this.getX(), this.getY()+1) && dungeon.getBoulder(this.getX(), this.getY() + 1) == null) {
                y().set(this.getY()+1);
                return true;
            }
        } else if (direction == "left") {
            if (!dungeon.occupied(this.getX()-1, this.getY()) && dungeon.getBoulder(this.getX() - 1, this.getY()) == null) {
                x().set(this.getX()-1);
                return true;
            }
        } else if (direction == "right") {
            if (!dungeon.occupied(this.getX()+1, this.getY()) && dungeon.getBoulder(this.getX() + 1, this.getY()) == null) {
                x().set(this.getX()+1);
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return Coordinate object
     */
    public Coordinate getCoordinate( ) {
        return c;
    }
}