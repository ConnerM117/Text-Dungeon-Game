package rooms;

import floors.Floor;
import items.consumables.Rope;
import mobs.Player;

public class Ravine extends SplitRoom {

	private String rope;
	private boolean isRoped;
	private int fallDamage;
	
	public Ravine(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ravine";
		isRoped = false;
		fallDamage = 3;
		sideOneDescription = "A gaping chasm splits the dungeon, cutting across your path. It's unclear if it was here before the dungeon's "
				+ "construction or if the earth split thereafter.";
		sideTwoDescription = sideOneDescription;
		
		rope = new Rope().getName();
	}

	@Override
	public String getCompletedDescription() {
		return "You... can't complete this room. Impressive.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction cross = new RoomAction("Cross the Ravine") {
			@Override
			public String resolveAction(Player player) {
				switchSide();
				if (isRoped) {
					return "You use the secured rope to cross safely to the other side of the ravine.";
				} else if (player.isStrong()) {
					return "You manage to climb down into the ravine and back up the other side without incident.";
				} else {
					String str = player.takeDamage(fallDamage);
					str += "\nYou begin to climb down the ravine's side, but partway down your foot slips and you lose your handhold. "
							+ "You plummet to the bottom, but thankfully it's not as deep as it first seemed. At least you don't fall "
							+ "again climbing the opposite side.";
					return str;
				}
			}
		};
		
		roomActions.add(cross);
		
		if (player.getInventory().containsKey(rope)) {
			RoomAction useRope = new RoomAction("Use Rope") {
				@Override
				public String resolveAction(Player player) {
					if (isRoped) {
						return "You've already tied a rope down, allowing you to traverse the chasm.";
					} else {
						player.removeFromInventory(rope);
						isRoped = true;
						return "You tie the rope securely to a large rock, allowing you to traverse the chasm safely; even if you were "
								+ "to fall, the rope would catch you.";
					}
				}
			};
			
			roomActions.add(useRope);
		}
	}
	
}
