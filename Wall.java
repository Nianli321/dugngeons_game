package unsw.dungeon;

public class Wall extends Entity implements Obstacle {
    private Coordinate c;
    public Wall(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.dungeon.addObstacle(this);
        this.c = new Coordinate(x, y);
    }

    // retuen the Coordinate of the Wall
    public Coordinate getCoordinate() {
        return c;
    }
}
