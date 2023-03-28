package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class DruidTower extends Room {
	
	public DruidTower(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Druid Tower";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "The old stone tower blocks the way downward, and has nothing of interest.";
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
