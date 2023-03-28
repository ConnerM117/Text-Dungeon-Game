package items.consumables;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public class EldritchEye extends Consumable {
	
	public EldritchEye() {
		name = "Eldritch Eye";
		description = "Take an action for free, once per turn.";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 7;
	}
	
	public String useItem(Player player) {
		if (!GameScreen.combatIsRunning) { //if combat isn't running
			return name + " has no use outside of combat!";
		} else {
			player.setHasSecondAction(true);
			player.removeFromInventory(this);
			return "The " + name + " crumbles to dust, and time slows around " + player.getName() + "!";
		}
	}

}
