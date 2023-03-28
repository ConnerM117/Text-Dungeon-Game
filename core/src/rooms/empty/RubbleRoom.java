package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class RubbleRoom extends Room {

	public RubbleRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Rubble";
	}
	
	@Override
	public String getDescription() {
		return "Part of the dungeon has collapsed. Stone lies in piles, and dust and dirt coats the area.";
	}

	@Override
	public String getCompletedDescription() {
		return "You... can't complete this area. Impressive.";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}

}
