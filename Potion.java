package unsw.dungeon;

public class Potion extends Entity implements Item{
	/**
	 * comment added from Nian Li
	 * at this stage, set duration as integer, might change the type of duration later
	 */
	
	public Potion(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		this.dungeon.addEntity(this);
	}

	@Override
    public void rmFromMap() {
	  dungeon.rmEntity(this);
	  this.setInvisible();

    }


}