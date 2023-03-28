package rooms;

import core.Chest;
import floors.Floor;
import mobs.Player;
import traps.Trap;

public class TrappedChest extends Room {

	public TrappedChest(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Trapped Chest";
		hasChest = true;
		chest = new Chest(roomNumber, Chest.Type.TRAPPED, 3);
		hasTrap = true;
		trap = Trap.getRandRoomTrap();
	}
	
	@Override
	public String getDescription() {
		if (chest.isEmpty())
			return "The hallway branches. Down the right fork, the hall turns and continues deeper into the catacombs, but to the left "
					+ "is a dead end with a chest. You've already taken everything of value.";
		else
			return "The hallway branches. Down the right fork, the hall turns and continues deeper into the catacombs, but to the left "
				+ "is a dead end with a chest. It's very obviously a trap for grave robbers- but there's very likely something "
				+ "good in the chest.";
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
