package unsw.dungeon;

public class Portal extends Entity {
    private int id;
    public Portal(Dungeon dungeon, int x, int y, int id) {
		super(x, y);
		this.dungeon = dungeon;
		this.dungeon.addEntity(this);
		this.id = id;
	}


	/**
	 * 
	 * @return id of portal
	 */
	public int getID() {
		return id;
	}

	//return Corresponding Portal
	public Portal getCorrespondingPortal (Entity self) {
		for (Entity en : dungeon.getEntities()) {
			if (self != en && en instanceof Portal) {
				Portal p = (Portal) en;
		
				if (p.getID() == this.id) {
					return p;
				}	
			}
		}
		return null; 
	}

	//teleport Player 
	public void TeleportPlayer (Player p) {
		Portal por = this.getCorrespondingPortal(this);
		p.jump(por.getX(), por.getY());
	}

}