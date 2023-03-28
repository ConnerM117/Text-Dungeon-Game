package rooms.lairs;

import floors.Floor;
import items.Coins;
import mobs.*;
import rooms.Room;

public class DragonNest extends Room {
	
	private int minCoins;
	private int maxCoins;
	
	public DragonNest(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dragon Nest";
		minCoins = 3;
		maxCoins = 6;
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nThe nest is lined with gold!\n";
		str += player.addToInventory(new Coins(minCoins, maxCoins));
		return str;
	}
	
	@Override
	public String getDescription() {
		return "This place is uncomfortably warm. There's a large pile of funguses tamped down into a bed, lined with "
				+ "things that glitter; this must be a dragon nest.";
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
