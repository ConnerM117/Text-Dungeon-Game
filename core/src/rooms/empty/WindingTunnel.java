package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class WindingTunnel extends Room {

	public WindingTunnel(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Winding Tunnel";
	}
	
	@Override
	public String getDescription() {
		return "The tunnel extends farther than you can see, turning up ahead into the encroaching dark.";
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
