package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class ForestClearing extends Room {

	public ForestClearing(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Forest Clearing";
	}
	
	@Override
	public String getDescription() {
		return "The trees part into a large clearing, lit as if by soft moonlight. Crickets chirp around you.";
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
