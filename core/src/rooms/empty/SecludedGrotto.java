package rooms.empty;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class SecludedGrotto extends Room {

	public SecludedGrotto(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Secluded Grotto";
	}
	
	@Override
	public String getDescription() {
		return "Massive boulders or cliffs- you can't tell which- separate this eerily serene grotto from the surrounding swamp.";
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
