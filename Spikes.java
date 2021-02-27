package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;

public class Spikes extends Entity implements Obstacle {

    private Dungeon dungeon;
    private Coordinate c;
    private SpikeState spikeState; 
    private BooleanProperty spikeUp = new SimpleBooleanProperty();
    private ImageView view;


    /**
     * constructor for spikes
     * set spikeState to down initially
     * @param dungeon the dungeon spikes are located in
     * @param x coordinate
     * @param y coordinate
     */
    public Spikes(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.c = new Coordinate(x, y);
        this.spikeState = new SpikeDownState(this);
        this.spikeUp.setValue(false);
    }

    /**
     * get coordinates
     * @return Coordinate object
     */
    @Override
    public Coordinate getCoordinate() {
        return c;
    }

    /**
     * change the spike state to up
     * change only needed one way
     */
    public void setSpikesUp() {
        spikeState = spikeState.putUpSpikes();
        spikeUp.setValue(true);
    }

    public void setImageView(ImageView view) {
        this.view = view;
    }

    /**
     * find imageView related to entity
     * @return view
     */
    public ImageView getImageView() {
        return view;
    }

    /**
     * return spike state
     * @return spikeUp
     */
    public BooleanProperty getSpikeState() {
        return spikeUp;
    }

}
