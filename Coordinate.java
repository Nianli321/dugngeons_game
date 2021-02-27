package unsw.dungeon;

public class Coordinate {
    private int x;
    private int y;
    
    /**
     * constructor for Coordinate
     * @param x coordinate
     * @param y coordinate
     */
    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * get the x coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * get the y coordinate
     * @return y coordinate
     */
    public int getY() {
        return y;
    }


    //compare two Coordinate
    /**
     * compare two objects and see if they share the same coordinates
     * @param obj 
     * @return boolean true if same coordinates, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        Coordinate c = (Coordinate) obj;
        return (this.x == c.x && this.y == c.y);
    }
}