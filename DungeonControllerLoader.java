package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    //Images
    private Image playerImage;
    private Image wallImage;
    private Image exitImage;
    private Image boulderImage;
    private Image switchImage;
    private Image treasureImage;
    private Image portalImage;
    private Image enemyImage;
    private Image swordImage;
    private Image potionImage;
    private Image doorImage;
    private Image keyImage;
    private Image dragonImage;
    private Image spikesDownImage;
    private static ImageView playerImageView;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        enemyImage = new Image((new File("images/deep_elf_master_archer.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        potionImage = new Image((new File("images/brilliant_blue_new.png")).toURI().toString());
        doorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        dragonImage = new Image((new File("images/dragon.png")).toURI().toString());
        spikesDownImage = new Image((new File("images/spikes_down.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
        view.setViewOrder(ZLayer.PLAYER.getZIndex());
        playerImageView = view;
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
        view.setViewOrder(ZLayer.MOVEABLES.getZIndex());
    }

    @Override
    public void onLoad(Switch plate) {
        ImageView view = new ImageView(switchImage);
        addEntity(plate, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
        view.setViewOrder(ZLayer.ITEMS.getZIndex());
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    @Override
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
        view.setViewOrder(ZLayer.PLAYER.getZIndex());
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
        view.setViewOrder(ZLayer.ITEMS.getZIndex());

    }

    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
        view.setViewOrder(ZLayer.ITEMS.getZIndex());
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        door.setImageView(view);
        // entities.add(viewOpen);
        addEntity(door, view);
        //trackDoorOpen(door, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
        view.setViewOrder(ZLayer.ITEMS.getZIndex());
    }

    @Override
    public void onLoad(Dragon dragon) {
        ImageView view = new ImageView(dragonImage);
        addEntity(dragon, view);
        view.setViewOrder(ZLayer.PLAYER.getZIndex());
    }

    @Override
    public void onLoad(Spikes spikes) {
        ImageView view = new ImageView(spikesDownImage);
        spikes.setImageView(view);
        addEntity(spikes, view);
        view.setViewOrder(ZLayer.OBSTACLES.getZIndex());
    }

    /**
     * add entity to entity list
     * track position and visibility of the entity
     * @param entity
     * @param view
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        trackVisibility(entity, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * @param entity
     * @param node
     */
    public static void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * track if an entity changes its visibility
     * and make it invisible
     * @param entity
     * @param node
     */
    private void trackVisibility(Entity entity, Node node) {
        entity.getBooleanProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                node.setVisible(false);
            }
        });
    }
    

    /**
     * return the Image View of the player
     * 
     * @return ImageView
     */
    public static ImageView getPlayerImageView() {
        return playerImageView;
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(Stage s, String dungeon_name) throws FileNotFoundException {
        return new DungeonController(load(), entities, s, dungeon_name);
    }


}
