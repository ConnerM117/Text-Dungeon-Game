package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class GoblinDen extends Room {
	
	public GoblinDen(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Goblin Den";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something interesting in one of the dwellings...\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getGoblinItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "You're greeted by the unmistakable stink of too many animals living in too small a space. Crude tent-like structures, "
				+ "likely crafted from the hides of giant rats and other subterranean abominations, stand in a circle.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}

}
