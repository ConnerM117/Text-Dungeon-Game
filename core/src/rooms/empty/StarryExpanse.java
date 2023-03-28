package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class StarryExpanse extends Room {

	public StarryExpanse(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Starry Expanse";
	}

	@Override
	public String getDescription() {
		return "You can't see the walls of the dungeon anymore. Are you even in the dungeon anymore? Motes of light float by like stars. "
				+ "In the distance, in every direction, you see what looks like the night sky, only clearer and more colorful, and "
				+ "beyond it is a hungry and gaping void.";
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
