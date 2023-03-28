package rooms;

import floors.Floor;
import items.consumables.Rope;
import mobs.Player;

public class Quicksand extends SplitRoom {

	private String rope;
	private boolean isRoped;
	private int fatigue;
	
	public Quicksand(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Quicksand";
		sideOneDescription = "Ahead is a very large patch of... mud? But it's got an odd sheen to it. Quicksand, probably.";
		sideTwoDescription = sideOneDescription;
		
		rope = new Rope().getName();
		isRoped = false;
		fatigue = 2;
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction cross = new RoomAction("Cross") {
			@Override
			public String resolveAction(Player player) {
				if (isRoped) {
					switchSide();
					return "You use the secured rope to cross safely to the other side of the quicksand, pulling yourself out when "
							+ "necessary.";
				} else if (player.isStrong()) {
					switchSide();
					return "You manage to evade most of the quicksand, though you do need to pull yourself out at the end.";
				} else {
					String str = player.takeFatigue(fatigue);
					str += "\nYou begin to cross, but don't get far before falling in. You barely manage to pull yourself back out, "
							+ "and it's a real fight that leaves you winded.";
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
						return "You've already tied a rope down, allowing you to traverse the quicksand.";
					} else {
						player.removeFromInventory(rope);
						isRoped = true;
						return "You tie the rope securely to a large tree, allowing you to traverse the area safely; if you were "
								+ "to fall in, you could use the rope to pull yourself out.";
					}
				}
			};
			
			roomActions.add(useRope);
		}
	}

}
