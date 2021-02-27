package unsw.dungeon;
// the good thing of using Item to group up al the collectable entities is that instead of having different kinds of collect-item functions, we only need one 
// loop, that is "if entity is the instance of Item, player.collect()" you can find it out in player's update method
public interface Item {

    public void rmFromMap();
    
}