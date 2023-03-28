package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class HydraLair extends Room {
	
	public HydraLair(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Hydra Liar";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "This swampy cavern has a large pool in the center filled with dirty, bubbling water. The hydra's lair.";
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
