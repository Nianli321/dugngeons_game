package unsw.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
//import sun.security.util.math.intpoly.IntegerPolynomial;
import javafx.beans.property.SimpleMapProperty;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;


/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    //private Dungeon dungeon;

    private List<Item> inventory;
    private IntegerProperty numKey;
    private IntegerProperty numTreasure;

    
    private BooleanProperty isInvincible;
    private BooleanProperty isDead;
    private IntegerProperty SwordHP;
    private BooleanProperty holdsSword;

    //private MediaPlayer hitSoundPlayer;

    /**
     * Create a player positioned in square (x,y)
     * @param x coordinate
     * @param y coordinate
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.dungeon.setPlayer(this);
        this.isInvincible = new SimpleBooleanProperty(false);
        this.inventory = new ArrayList<Item>();
        this.isDead = new SimpleBooleanProperty();
        this.isDead.set(false);
        this.numKey = new SimpleIntegerProperty(0);
        this.numTreasure = new SimpleIntegerProperty(0);
        this.SwordHP = new SimpleIntegerProperty(0);
        this.holdsSword = new SimpleBooleanProperty(false);
    }


    


    // /**
    //  * comment added by Nian Li
    //  * assume player will automatically add item in his inventory 
    //  * and the volum of inventory is unlimited
    //  * @param i item
    //  */

    /**
     * set player to be invincible
     * used when picking up a potion
     * @param condition
     */
    public void setInvincible(Boolean condition) {
        this.isInvincible.setValue(condition);
    }

    /**
     * find invincible status
     * @return Boolean
     */
    public Boolean getInvincible() {
        return isInvincible.getValue();
    }

    /**
     * return list of inventory
     * @return players inventory
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * find BooleanProperty describing dead state of player
     * @return BooleanProperty isDead
     */
    public BooleanProperty getIsDead() {
        return isDead;
    }

    /**
     * function that moves enemies towards player if not invincible
     * and moves away from player if invincible
     */
    public void moveEnemies() {
        // get enemies to move
        //go through list of entities
        for (Entity e1: dungeon.getEntities()) {
            // for every enemy
            if (e1 instanceof Enemy) {
                Enemy enemy = (Enemy) e1;
                // move away from player if invincible
                if(isInvincible.get()) {enemy.moveAway();}
                // otherwise move towards the player
                else {enemy.moveNormal();}
            }
        }        
    }

    /**
     * update function that updates status of 
     * any entity that is walked on
     */
    public void update () {
        for (Entity e1: dungeon.getEntities()) {
            // update all switches
            if (e1 instanceof Switch) {
                Switch s1 = (Switch) e1;
                s1.update();
            }
        }

        dungeon.updateGoal();

        Entity en = this.UnderMe(this);
        if (en == null) {
        
            return;
        }
        else if (en instanceof Item) {
            Item i = (Item) en;// cast en as Item
            this.collect(i);
            if (en instanceof Potion) {
                this.usePotion();
            }
        }
        else if (en instanceof Portal) {
            Portal p = (Portal) en;// cast en as Portal
            p.TeleportPlayer(this);// Assume every Portal will have one and only one Corresponding portal
            playSound("media/portal.mp3");
        }
        else if (en instanceof Enemy) {
            // collide with Enemy
            // player dies
            this.die(); 
        }
        else if (en instanceof Spikes) {
            this.die();
            Spikes spikes = (Spikes) en;
            //set state of spikes to up
            spikes.setSpikesUp();

        }

        // check if goals are complete
        dungeon.updateGoal();
    }

    /**
     * 
     * @return sword item
     */
    public Item getSword() {
        for (Item i : inventory) {
            if (i instanceof Sword) {
                return i;
            }
        }
        return null;
    }


    /**
     * move player up
     */
    public void moveUp() {
        moveDirection(getX(), getY()-1, "up");
    }

    /**
     * move player down
     */
    public void moveDown() {
        moveDirection(getX(), getY()+1, "down");
    }

    /**
     * move player left
     */
    public void moveLeft() {
        moveDirection(getX()-1, getY(), "left");
    }

    /**
     * move player right
     */
    public void moveRight() {
        moveDirection(getX()+1, getY(), "right");
    }

    /**
     * move the player in the given direction
     * checks to see if player can move into square
     * @param x coordinate
     * @param y coordinate
     * @param direction up, down, left, right
     */
    public void moveDirection(int x, int y, String direction) {
        Door d = dungeon.getDoor(x, y);
        //if locked door exists, attempt unlock
        if (d != null) {
            //loop through keys in inventory?
            for (Item i: inventory) {
                if (i instanceof Key) {
                    
                    Key k = (Key) i;
                    if (d.tryToOpen(k.getID()))  {
                        //this.numKey.set(this.numKey.get() - 1);
                        rmItem(i);
                        playSound("media/unlock.mp3");
                        break;
                        //changeNumKey(-1);
                        
                    }
                }
            }
        }

        // if space is a boulder, check if boulder can move, if not skip
        Boulder b = dungeon.getBoulder(x, y);
        // if space is not a boulder
        if (b == null) {
            // check if not occupied and in boundary
            if (!dungeon.occupied(x, y) && dungeon.inBoundary(x, y)) {
                x().set(x);
                y().set(y);
            }
        } else {                            // if space is a boulder
            // try to move boulder, if boulder moves update location
            if (b.tryMove(direction) == true) {
                x().set(x);
                y().set(y);
                playSound("media/boulder.mp3");
            }
            // if boulder doesn't move, dont do anything
        }
        this.update();
    }

    /**
     * jump player to given coordinates
     * @param x coordinate
     * @param y coordinate
     */
    public void jump(int x, int y) {
        if (this.dungeon.occupied(x, y) == false) {
            this.setX(x);
            this.setY(y);
        }
    }
    
    /**
     * collect the item and carry out initial functions
     * @param item item that player has walked on
     */
    public void collect (Item item) {
        inventory.add(item);
        item.rmFromMap();
        if (item instanceof Key) {
            changeNumKey(1);
        } else if (item instanceof Treasure) {
            changeNumTreasure(1);
        } else if (item instanceof Sword) {
            this.SwordHP.set(5);
            this.holdsSword.set(true);
        }
        playSound("media/treasure.mp3");
    }

    /**
     * use the potion
     * set invincible
     * set 20 second timer
     */
    public void usePotion() {
        playSound("media/Gulp.mp3");
        for (Item i: inventory) {
            if (i instanceof Potion) {
                //change player state to invincible
                this.isInvincible.set(true);
                //start timer
                dungeon.startTimer(this, "timer", 20);
                //remove potion from player inventory
                inventory.remove(i);
                return;
            }
        }
    }

    /**
     * attack enemies in four squares around the player
     * up, down, left, and right
     * Only works when sword is held
     */
    public void attack() {
        playSound("media/hit.mp3");
        Sword s = holdSword();
        if (s != null) {
            
            Enemy one = dungeon.getEnemy(getX() - 1, getY());
            Enemy two = dungeon.getEnemy(getX(), getY() - 1);
            Enemy three = dungeon.getEnemy(getX() + 1, getY());
            Enemy four = dungeon.getEnemy(getX(), getY() + 1);
            if (one != null || two != null || three != null || four != null) {
                s.loseHP();
                this.SwordHP.set(this.SwordHP.get() - 1);
            }

            if (one != null) {
                one.die();
            } 
            
            if (two != null) {
                two.die();
            }
            
            if (three != null) {
                three.die();
            }
            
            if (four != null) {
                four.die();
            }

        }
    }

    /**
     * return the sword in inventory
     * @return sword
     */
    public  Sword holdSword() {
        for (Item i : inventory) {
            if (i instanceof Sword) {
                return (Sword) i;
            }
        }
        return null;
    }

    /**
     * remove item from inventory
     * @param item item to be removed
     */
    public void rmItem(Item item) {
        inventory.remove(item);
        if (item instanceof Key) {
            changeNumKey(-1);    
        } else if (item instanceof Treasure) {
            changeNumTreasure(-1);
        } else if (item instanceof Sword) {
            this.holdsSword.set(false);
        }
    }

    /**
     * update number of keys in inventory
     * @param i number to be updated to
     */
    private void changeNumKey(int i) {
        this.numKey.set(this.numKey.get() + i);
    }

    /**
     * update number of treasure in inventory
     * @param i number to be updated to
     */
    private void changeNumTreasure(int i) {
        this.numTreasure.set(this.numTreasure.get() + i);
    }

    /**
     * find the number of keys in inventory
     * @return numKey integerProperty
     */
    public IntegerProperty getNumKeyP() {
        return this.numKey;
    }

    /**
     * find the number of treasure in inventory
     * @return numTreasure integerProperty
     */
    public IntegerProperty getNumTreasureP() {
        return this.numTreasure;
    }

    /**
     * find the health of the sword in inventory
     * @return sword HP
     */
    public IntegerProperty getSwordHP() {
        return this.SwordHP;
    }

    /**
     * get invincible status of player
     * @return isInvincible true if invincible false if not
     */
    public BooleanProperty getIsInviciableBP() {
        return this.isInvincible;
    }

    /**
     * get sword hold status of player
     * @return holdsSword
     */
    public BooleanProperty getHoldSword() {
        return this.holdsSword;
    }


    /**
     * player dies
     * remove player from dungeon and set dead status to true
     */
    public void die ( ) {
        playSound("media/death.mp3");
        dungeon.rmEntity(this);
        this.isDead.set(true);
    }

    public void playSound(String media) {
        // MediaPlayer hitSoundPlayer = new MediaPlayer(new Media(new File(media).toURI().toString()));
        // hitSoundPlayer.setVolume(5);
        // hitSoundPlayer.play();
        return;
    }

}


