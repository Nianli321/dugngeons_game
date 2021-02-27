package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        /**
         * comment from Nian Li
         * add something here to get the goal from json
         * for example: 
         * JSONObject goal = json.getJSONObject("goal-condition");
         * DONE
         */
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }

        Goal dungeonGoal = loadGoal(dungeon, json.getJSONObject("goal-condition"));
        dungeon.setGoal(dungeonGoal);
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;
        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            onLoad(enemy);
            entity = enemy;
            break;
        case "wall":
            Wall wall = new Wall(dungeon, x, y);
            onLoad(wall);
            entity = wall;
            break;
        // DONE Handle other possible entities
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            onLoad(exit);
            entity = exit;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "switch":
            Switch floorSwitch = new Switch(dungeon, x, y);
            onLoad(floorSwitch);
            entity = floorSwitch;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(dungeon, x, y, id);
            onLoad(door);
            entity = door;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(dungeon, x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            onLoad(sword);
            entity = sword;
            break;
        case "potion":
            Potion potion = new Potion(dungeon, x, y);
            onLoad(potion);
            entity = potion;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(dungeon, x, y, id);
            onLoad(key);
            entity = key;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            onLoad(treasure);
            entity = treasure;
            break;
        case "dragon":
            Dragon dragon = new Dragon(dungeon, x, y);
            onLoad(dragon);
            entity = dragon;
            break;
        case "spikes":
            Spikes spikes = new Spikes(dungeon, x, y);
            onLoad(spikes);
            entity = spikes;
            break;
        }
    }

    private Goal loadGoal(Dungeon dungeon, JSONObject json) {


        
        String goal = json.getString("goal");
        JSONArray subgoals;
        Goal subGoal = null;
        switch (goal) {
        case "exit":
            ExitGoal exitGoal = new ExitGoal();
            return exitGoal;
        case "enemies":
            EnemyGoal enemyGoal = new EnemyGoal();
            return enemyGoal;
        case "boulders":
            SwitchGoal switchGoal = new SwitchGoal();
            return switchGoal;
        case "treasure":
            TreasureGoal treasureGoal = new TreasureGoal();
            return treasureGoal;
        case "AND":
            ComplexGoal andGoal = new ComplexGoal("AND");
            subgoals = json.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                subGoal = loadGoal(dungeon, subgoals.getJSONObject(i));
                andGoal.addGoal(subGoal);
            }
            return andGoal;

        case "OR":
            ComplexGoal orGoal = new ComplexGoal("OR");
            subgoals = json.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                subGoal = loadGoal(dungeon, subgoals.getJSONObject(i));
                orGoal.addGoal(subGoal);
            }
            return orGoal;
        }
        return subGoal;
    }

    public abstract void onLoad(Entity player);

    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Boulder boulder);
    
    public abstract void onLoad(Switch plate);

    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Sword sword);

	public abstract void onLoad(Portal portal);

	public abstract void onLoad(Potion potion);
	
	public abstract void onLoad(Door door);

    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Dragon dragon);

    public abstract void onLoad(Spikes spikes);

    // DONE Create additional abstract methods for the other entities

}
