package unsw.dungeon;

public class Treasure extends Entity implements Item {
    /**
     * comment added by Nian Li
     * at this stage I have find and suitable fileds.
     * However, my idea is to use the info from dungeon to find out if the location of player overlaps with Treasure, 
     * if so remove Treasure from dungeon and add it to player's inventory 
     */
    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
    }

    @Override
    public void rmFromMap() {
      dungeon.rmEntity(this);
      this.setInvisible();  
    }
    
}