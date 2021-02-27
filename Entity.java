package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty visibility;

    protected Dungeon dungeon;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.visibility = new SimpleBooleanProperty(true);
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    //set entity to be invisible
    public void setInvisible() {
        this.visibility.set(false);
    }

    public BooleanProperty getBooleanProperty() {
        return this.visibility;
    }

    public boolean getVisibility() {
        return this.visibility.getValue();
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public void setY(int y) {
        this.y.set(y);
    }

    public void setX(int x) {
        this.x.set(x);
    }

    

    // return the object that is under me, if nothing is under me return null
    public Entity UnderMe(Entity self) {
        for(Entity en : dungeon.getEntities()) {
            if (en != self && en.getX() == this.getX() && en.getY() == this.getY()) {
                return en;
            }
        }
        return null;
    }


}
