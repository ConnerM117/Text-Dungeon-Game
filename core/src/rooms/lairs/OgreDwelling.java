package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class OgreDwelling extends Room {
	
	public OgreDwelling(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ogre Dwelling";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something interesting in one of the pallets...\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getTrollItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "The cavern is lined with bed pallets made of bones and anything the denizens have been able to harvest in the dark: "
				+ "mainly various funguses and moss. The monsters must live here; to linger is to invite risk.";
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
