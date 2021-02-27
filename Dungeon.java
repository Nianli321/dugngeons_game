/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Port;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<Obstacle> obstacles;
    private Goal goal;
    private BooleanProperty isComplete;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
        this.obstacles = new ArrayList<Obstacle>();
        this.isComplete = new SimpleBooleanProperty();
        this.isComplete.set(false);
    }

    /**
     * get width of dungeon
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * get height of dungeon
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * return player in dungeon
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * set the player of the dungeon
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    /**
     * return if dungeon complete
     * @return true if complete
     */
    public boolean getIsComplete() {
        return isComplete.get();
    }

    /**
     * add entity to entitiy list
     * @param entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    /**
     * remove entity from entity list
     * @param entity
     */
    public void rmEntity(Entity entity) {
        entities.remove(entity);
    }
    /**
     * add obstacle to obstacle list
     * @param obstacle
     */
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }
    /**
     * remove obstacle form obstacle list
     * @param obstacle
     */
    public void rmObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }

    /**
     * set goal of dungeon
     * @param goal
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * get the goal of the dungeon
     * @return
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * check if square is occupied by immoveable objects
     * @param x coordinate
     * @param y coordinate
     * @return boolean
     */
    public boolean occupied(int x, int y) {
        // check if coordinates are outside of boundaries
        if (!inBoundary(x, y)) return false;
        //Coordinate c = new Coordinate(x, y);
        for (Obstacle o: this.obstacles) {
            if (o.getCoordinate().getX() == x && o.getCoordinate().getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the door at given coordinate
     * @param x coordinate
     * @param y coordinate
     * @return door
     */
    public Door getDoor(int x, int y) {
        //Coordinate c = new Coordinate(x, y);
        for (Obstacle o: this.obstacles) {
            if (o instanceof Door) {
                Door d = (Door) o;
                if (d.getX() == x && d.getY() == y) {
                    return d;
                }
            }
        }
        return null;
    }

    /**
     * get the boulder at a given coordinate
     * @param x coordinate
     * @param y coordinate
     * @return boulder
     */
    public Boulder getBoulder(int x, int y) {
        //Coordinate c = new Coordinate(x, y);
        for (Entity en: entities) {
            if (en instanceof Boulder) {
                Boulder b = (Boulder) en;
                if (b.getX() == x && b.getY() == y) {
                    return b;
                }
            }
        }
        return null;
    }

    /**
     * get portal at given location
     * @param x coordinate
     * @param y coordinate
     * @return
     */
    public Portal getPortal(int x, int y) {
        for (Entity en: entities) {
            if (en instanceof Portal) {
                Portal p = (Portal) en;
                if (p.getX() == x && p.getY() == y) {
                    return p;
                }  
            }
        }
        return null;
    }



    /**
     * get exit of dungeon assuming one exit
     * @return
     */
    public Exit getExit() {
        for (Entity en: entities) {
            // need getCoordinate to work here, maybe putting it in entity would work
            if (en instanceof Exit) {
                return (Exit) en;
            }
        }
        return null;
    }

    /**
     * get list of entities of dungeon
     * @return entities
     */
    public List<Entity> getEntities() {
        return entities;
    }


   /**
    * check if all enemies are dead
    * @return boolean
    */
    public boolean goalEnemies() {
        for (Entity en : entities) {
            if (en instanceof Enemy) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if all switches are pressed
     * @return boolean
     */
    public boolean goalSwitches() {
        for (Entity en : entities) {
            if (en instanceof Switch) {
                Switch s = (Switch) en;
                if (s.triggered() == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * check if all treasure is collected
     * @return boolean
     */
    public boolean goalTreasure() {
        for (Entity en : entities) {
            if (en instanceof Treasure) {
                return false;
            }
        }
        return true;
    }    

    /**
     * check enemy at given location
     * @param x coordinate
     * @param y coordinate
     * @return enemy
     */
    public Enemy getEnemy(int x, int y) {

        for (Entity en: entities) {
            if (en instanceof Enemy) {
                Enemy e = (Enemy) en;
                if (e.getX() == x && e.getY() == y) {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * get complete Boolean Property
     * @return BooleanProperty
     */
    public BooleanProperty getIsCompleteBP() {
        return this.isComplete;
    }

    /**
     * update the goal of the dungeon
     */
    public void updateGoal() {
        isComplete.set(goal.checkGoal(this, player));
    }

    /**
     * timer that counts down 20 seconds for potion to start
     * it triggers PotionTimer at 20 seconds, and will then set isInvinvible to false
     * @param p
     */
    public void startTimer(Player p, String task, int seconds) {
        Timer timer = new Timer();
        timer.schedule(new PotionTimer(p, task), seconds*1000);
    }

    /**
     * take coordinate and check if it is in the dungeon boundaries
     * @param x
     * @param y
     * @return
     */
    public Boolean inBoundary(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return false;
        return true;
    }
}
