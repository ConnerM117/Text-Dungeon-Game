package rooms;

import floors.Floor;
import items.consumables.Rope;
import mobs.Player;

public class RockySlope extends SplitRoom {

	private String rope;
	private boolean isRoped;
	private int fallDamage;
	
	public RockySlope(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Rocky Slope";
		isRoped = false;
		fallDamage = 3;
		sideOneDescription = "A tall rocky slope blocks the way forward. If you move forward, you'll have to hope you can "
				+ "make it down the slope without injury.";
		sideTwoDescription = "A tall rocky slope blocks the way forward. There's no way to climb it without a tool of some kind.";
		
		Rope rope = new Rope();
		this.rope = rope.getName();
	}

	@Override
	public String getCompletedDescription() {
		return "You... can't complete this room. Impressive.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction cross = new RoomAction("Traverse the Slope") {
			@Override
			public String resolveAction(Player player) {
				if (isRoped) {
					String str = "";
					if (roomSide == RoomSide.SIDE_ONE)
						str += "You use the rope to safely rappel down the slope.";
					else
						str += "You use the rope to climb up the treacherous slope.";
					switchSide();
					return str;
				} else if (roomSide == RoomSide.SIDE_TWO) { 
					return "The slope is too treacherous to climb. Perhaps if you were to secure something at the top?";
				} else if (roomSide == RoomSide.SIDE_ONE && player.isStrong()) {
					switchSide();
					return "You manage to climb down the slope carefully, without injury.";
				} else {
					String str = player.takeDamage(fallDamage);
					str += "\nYou begin to climb down the slope, but partway down your foot slips and you lose your handhold. "
							+ "You fall a little ways, and then roll to a stop among the rocks with smaller ones clattering to "
							+ "a standstill around you.";
					return str;
				}
			}
		};
		
		roomActions.add(cross);
		
		//you can only secure the rope at the top if you're at the top and you have a rope
		if (player.getInventory().containsKey(rope) && roomSide == RoomSide.SIDE_ONE) {
			RoomAction useRope = new RoomAction("Use Rope") {
				@Override
				public String resolveAction(Player player) {
					if (isRoped) {
						return "You've already tied a rope down, allowing you to traverse the chasm.";
					} else {
						player.removeFromInventory(rope);
						isRoped = true;
						return "You tie the rope securely to a large rock, allowing you to traverse the slope safely; even if you were "
								+ "to slip, the rope would catch you.";
					}
				}
			};
			
			roomActions.add(useRope);
		}
		
	}

}
