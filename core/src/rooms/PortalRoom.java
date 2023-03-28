package rooms;

import floors.Floor;
import mobs.Player;

public class PortalRoom extends Room {

	public PortalRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Portal";
		hasPortal = true;
	}

	@Override
	public String getDescription() {
		return "An amorphous shape swirls in front of you. You can't see clearly through it, through you think you catch glimpses of "
				+ "different places: places you have already been, and places you have yet to be.";
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
