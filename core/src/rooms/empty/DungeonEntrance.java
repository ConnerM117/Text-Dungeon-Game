package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class DungeonEntrance extends Room {

	public DungeonEntrance(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dungeon Entrance";
		combatChance = NO_COMBAT_CHANCE;
	}

	@Override
	public String getDescription() {
		return "The light of the cave entrance behind you is lost as you round a corner. Your heartbeat quickens and your eyes flick "
				+ "to every shadow. There's no turning back now.";
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
