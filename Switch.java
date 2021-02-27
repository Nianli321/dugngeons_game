package unsw.dungeon;

public class Switch extends Entity {
	
	private Boolean isTriggered = false;
	

	public Switch(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
        this.dungeon.addEntity(this);
	}
	//update method for Switch
	public void update() {
		Entity en = this.UnderMe(this);
		if (en == null) {// nothing under switch, do nothing
			return;
		}
		else if (en instanceof Boulder) {//under Boulder. trigger
			this.trigger();
		} 
		else {//Boulder off, untrigger 
			this.untrigger();
		}
	}

	//show the status of the switch
	public Boolean triggered () {
		return isTriggered;
	}

	//trigger the switch
	public void trigger () {
		this.isTriggered = true;
	}

	//untrigger the switch
	public void untrigger () {
		this.isTriggered = false;
	}

	/**
	 * comment added by Nian Li
	 * trigger and untrigger method can be ommitted by using the information from dungeon
	 * the above idea might be implemented later
	 */
}