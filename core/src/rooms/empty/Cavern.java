package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class Cavern extends Room {

	public Cavern(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Cavern";
	}
	
	@Override
	public String getDescription() {
		return "You enter a large cavern, your steps echoing off the stone walls. Stalactites hang from the ceiling, and "
				+ "stalagmites point up to meet them like the teeth of some monstrous creature. It smells damp, and you "
				+ "can hear something dripping.";
	}

	@Override
	public String getCompletedDescription() {
		return "You... can't complete this room. Impressive.";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}
}
