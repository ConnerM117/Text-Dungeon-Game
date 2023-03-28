package rooms;

import floors.Floor;
import items.consumables.ArcaneText;
import mobs.Player;

public class Observatory extends Room {

	private String arcaneText;
	private int fatigue;
	
	public Observatory(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Observatory";
		
		arcaneText = new ArcaneText().getName();
		fatigue = 1;
	}

	@Override
	public String getDescription() {
		return "This room is a large hemisphere, like an upside-down bowl, but the walls and ceiling seem to be made entirely of one "
				+ "enormous warped mirror. In the middle is a desk next to a spindly magical contraption of some sort.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction useContraption = new RoomAction("Use Contraption") {
			@Override
			public String resolveAction(Player player) {
				if (player.getInventory().containsKey(arcaneText) || player.isMindful()) {
					//TODO do something cool!
					return "";
				} else {
					return "You fiddle with the contraption, but its purpose confounds you. The process wearies your "
							+ "body and mind. Pity there's no instruction manual lying about...\n" + player.takeFatigue(fatigue);
				}
			}
		};
		
		roomActions.add(useContraption);
		
	}

}
