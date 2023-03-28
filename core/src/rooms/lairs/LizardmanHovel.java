package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class LizardmanHovel extends Room {
	
	public LizardmanHovel(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Lizardman Hovel";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something interesting in the hut...\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getSwampItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "In the swamp, something appears out of the mist that looks like a low hut made of mud and scavenged branches. "
				+ "Likely a dwelling for the reptilian horrors that call this place home.";
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
