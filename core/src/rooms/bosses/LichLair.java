package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class LichLair extends Room {
	
	public LichLair(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Lich's Lair";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "This room doesn't look like it belongs in the dungeon. Bookshelves hold a plethora"
				+ "of books, and complex alchemy equipment bubbles and steams.";
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
