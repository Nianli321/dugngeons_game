package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Sword extends Entity implements Item {
    
    private int hitPoints;

    public Sword(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.hitPoints = 5;
    }
    
    /**
     * 
     * @return the health of the sword, 5 is the max, 0 is min
     */
    public int getHP () {
        return hitPoints;
    }

    /**
     * when a sword is used on an enemy, it loses 1 HP
     * when Hp == 0, remove itself
     */
    public void loseHP () {
        this.hitPoints --;
        if (getHP() == 0) {
            dungeon.rmEntity(this);
            dungeon.getPlayer().rmItem(this);
        }    
    }


    @Override
    public void rmFromMap() {
      dungeon.rmEntity(this);
      this.setInvisible();
    }

}