package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class RatKingThrone extends Room {
	
	public RatKingThrone(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Rat King's Throne Room";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "A makeshift throne made of rat bones stands against the wall, next to the tunnel that leads farther down.";
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
