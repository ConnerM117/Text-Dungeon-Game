package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class LongHallway extends Room {

	public LongHallway(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Long Hallway";
	}
	
	@Override
	public String getDescription() {
		return "The hall extends farther than you can see, into the grasping dark.";
	}

	@Override
	public String getCompletedDescription() {
		return "You... can't complete a hallway. Impressive.";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}

}
