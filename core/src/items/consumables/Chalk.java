package items.consumables;

import com.textdungeon.game.GameScreen;

import mobs.Player;

public class Chalk extends Consumable {

	public Chalk() {
		name = "Chalk";
		description = "Use to mark your way through part of the dungeon. When used in a room, passing through that room "
				+ "will not increase your Fatigue.";
		count = 1;
		cost = 1;
		isEquippable = false;
		type = Type.CONSUMABLE;
		
		atlasIndex = 5;
	}
	
	public String useItem(Player player) {
		if (GameScreen.currentRoom.isChalked()) {
			return "You've already used Chalk in this room.";
		} else {
			GameScreen.currentRoom.setChalked(true);
			player.removeFromInventory(this);
			return "You mark your way around this area carefully. It will be easier for you to navigate in the future.";
		}
	}
	
}
