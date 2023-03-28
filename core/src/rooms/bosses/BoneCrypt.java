package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class BoneCrypt extends Room {

	public BoneCrypt(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Forgotten Crypt";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "The crypt is eerily silent, stone sepulchers seemingly watching you in the dark.";
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
