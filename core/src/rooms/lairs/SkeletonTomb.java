package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class SkeletonTomb extends Room {
	
	public SkeletonTomb(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Skeleton Tomb";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something interesting in one of the sarcophagi...\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getSkeletonItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "You enter a sepulcher lined with stone sarcophagi. Some of them are opened, but contain no remains...";
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
