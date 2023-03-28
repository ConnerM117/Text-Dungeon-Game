package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class TempleSanctum extends Room {
	
	public TempleSanctum(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Temple Sanctum";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "Profane sigils coat the walls and dried blood betrays the fate of adventurers that came before you.";
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
