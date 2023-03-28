package rooms.lairs;

import floors.Floor;
import mobs.*;
import rooms.Room;

public class ForestGlade extends Room {
	
	public ForestGlade(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Forest Glade";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String getDescription() {
		return "You enter a forest clearing abundant with tracks and spoor that suggest large predatory animals come this way often. "
				+ "It may not be wise to linger.";
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
