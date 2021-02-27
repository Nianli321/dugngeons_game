package unsw.dungeon;

import java.util.TimerTask;

public class PotionTimer extends TimerTask{
    
    Player player;
    String task;

    public PotionTimer(Player p, String task) {
        this.player = p;
        this.task = task;
    }

    public void run() {
        if (this.task == "wait") {

        } else {
            player.setInvincible(false);
        }
        
    }
}