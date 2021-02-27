package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;

public class Door extends Entity implements Obstacle {
    
    //Assumption1: when the Door is just initialized, assume it to be closed 
    //Assumption2: once open, never close
    private BooleanProperty isOpen; 
    private int id;
    private Coordinate c;
    private ImageView view;

    public Door(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.dungeon.addObstacle(this);
        this.id = id;
        this.c = new Coordinate(x, y);
        this.isOpen = new SimpleBooleanProperty();
        this.isOpen.setValue(false);
    }

    //return the coordinate of the door
    public Coordinate getCoordinate () {
        return c;
    }

    // return the status of the door
    public Boolean getOpenStatus () {
        return isOpen.getValue();
    }
    
    // try to open the door, if the key id is different or 
    // the player has key, so nothing    
    public boolean tryToOpen (int id) {
        if (id == this.id) {
            setOpenStatus();
            return true;
        }
        return false;
    }

    // set the door status as open
    public void setOpenStatus () {
        this.isOpen.setValue(true);
        //remove itself from the Obstacle list in Dungeon
        dungeon.rmObstacle(this);
    }

    /**
     * set image view
     * @param view
     */
    public void setImageView(ImageView view) {
        this.view = view;
    }

    /**
     * return imageview related to this door
     * @return view
     */
    public ImageView getImageView() {
        return view;
    }

    public BooleanProperty getIsOpenBP() {
        return isOpen;
    }
}