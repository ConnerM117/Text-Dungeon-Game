package rooms.bosses;

import floors.Floor;
import mobs.*;
import rooms.Room;

public class DragonLair extends Room {
	
	public DragonLair(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dragon Lair";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "The cave opens into a cavernous hall. The dragon's lair.";
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
