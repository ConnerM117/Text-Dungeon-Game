package rooms.lairs;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class RatNest extends Room {
	
	public RatNest(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Rat Nest";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something interesting in one of the nests...\n";
		str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		return str;
	}
	
	@Override
	public String getDescription() {
		return "Your senses are assaulted by a foul smell before you enter a wide tunnel lined with nests made "
				+ "of stone, dirt, straw, bits of cloth, and other miscellany. The rats must nest here. There might be "
				+ "something valuable in the nests...";
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
