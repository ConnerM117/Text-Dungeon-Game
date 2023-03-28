package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class UndeadOutpost extends Room {
	
	public UndeadOutpost(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Undead Outpost";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nOne of the corpses is clutching something...\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getLichItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "The stench of death is stronger here, and it's no wonder why. Moldering corpses lie in piles in various states of decay. "
				+ "You stay on guard, twitching as shadows move in the periphery, lest one of them stand up...";
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
