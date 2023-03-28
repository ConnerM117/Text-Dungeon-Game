package items.consumables;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public class MakeshiftTrap extends Consumable {
	
	public MakeshiftTrap() {
		name = "Makeshift Trap";
		description = "When armed, damages your first attacker";
		count = 1;
		cost = 2;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 15;
	}
	
	public String useItem(Player player) {
		if (GameScreen.combatIsRunning) {
			player.setHasTrap(true);
			player.removeFromInventory(this);
			return player.getName() + " arms a " + name + "!";
		} else {
			return "A " + name + " is only useful during combat.";
		}
		
		
		
	}

}
