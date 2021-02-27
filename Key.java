package unsw.dungeon;

public class Key extends Entity implements Item{
    
    private int id;
    
    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeon.addEntity(this);
        this.id = id;
    }

    public int getID () {
      return this.id;
    }
    
    @Override
    public void rmFromMap() {
      dungeon.rmEntity(this);
      this.setInvisible();
    }
}