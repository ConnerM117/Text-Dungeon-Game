package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class GoblinKingThrone extends Room {
	
	public GoblinKingThrone(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Goblin King's Throne Room";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "In the center of the room stands a throne made of bits of wood, stone, and scrap metal. Behind it is the way farther down.";
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
