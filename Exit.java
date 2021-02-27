package unsw.dungeon;

public class Exit extends Entity {

    private Boolean open;

    /**
     * 
     * @param x integer coordinate
     * @param y integer coordinate
     */
    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.open = false;
    }

    /**
     * 
     * @return open, if true then exit is open and can be walked on, if false then exit is shut
     */
    public Boolean getOpenStatus () {
        return open;
    }

    /**
     * 
     * @param openStatus set door to the Boolean status of openStatus
     */
    public void setOpenStatus (Boolean openStatus) {
        this.open = openStatus;
    }
    
}