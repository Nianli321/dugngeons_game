package unsw.dungeon;

import java.lang.Math;
public class Enemy extends Entity {
	// will be implemented 
	// use state Pattern
	//State state;

	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
        this.dungeon.addEntity(this);
	}

	// if a Enemy dies, remove itself from Entity list 
	public void die() {
		dungeon.rmEntity(this);
		this.setInvisible();
	}

	//movement behaviour functions

	/**
	 * function to move the enemy towards the player
	 */
	public void moveNormal() {
		Player p = dungeon.getPlayer();
		// find the differences in the x coordinates and the y coordinates
		int xDiff = p.getX() - this.getX();
		int yDiff = p.getY() - this.getY();
		if (Math.abs(xDiff) < Math.abs(yDiff)) {
			//move y coordinate
			if (this.getY() > p.getY()) {
				moveUp();
			} else {
				moveDown();
			}
		} 
		else if (Math.abs(xDiff) == Math.abs(yDiff)) {
			if (this.getY() > p.getY() && this.getX() > p.getX()) {
				moveLeft();
			} else if (this.getY() < p.getY() && this.getX() > p.getX()) {
				moveLeft();
			} else if (this.getY() > p.getY() && this.getX() < p.getX()) {
				moveRight();
			} else if (this.getY() < p.getY() && this.getX() < p.getX()) {
				moveRight();
			}
		} 
		else {
			//move x coordinate
			if (this.getX() > p.getX()) {
				moveLeft();
			} else {
				moveRight();
			}
		}
		
	}

	/**
	 * function to move enemy away from the player
	 */
	public void moveAway() {
		Player p = dungeon.getPlayer();
		// find the differences in the x coordinates and the y coordinates
		int xDiff = p.getX() - this.getX();
		int yDiff = p.getY() - this.getY();
		if (Math.abs(xDiff) < Math.abs(yDiff)) {
			//move y coordinate
			if (this.getY() > p.getY()) moveDown();
			else moveUp();
		} else {
			//move x coordinate
			if (this.getX() > p.getX()) moveRight();
			else moveLeft();
		}
	}

	//movement functions

	public void moveUp() {
        moveDirection(getX(), getY()-1);
    }

    public void moveDown() {
        moveDirection(getX(), getY()+1);
    }

    public void moveLeft() {
        moveDirection(getX()-1, getY());
    }

    public void moveRight() {
        moveDirection(getX()+1, getY());
    }

	public void moveDirection(int x, int y) {
		// if the space is not occupied by and obstacle, move into it
		Boulder b = dungeon.getBoulder(x, y);
        if (!dungeon.occupied(x, y) && b == null) {
            x().set(x);
            y().set(y);
        }
    }


}