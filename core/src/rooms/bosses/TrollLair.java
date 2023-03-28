package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class TrollLair extends Room {
	
	public TrollLair(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Troll Lair";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "Your steps echo throughout the massive stone cavern, which stinks of troll.";
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
