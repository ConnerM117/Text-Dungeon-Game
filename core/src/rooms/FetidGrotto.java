package rooms;

import floors.Floor;
import items.Item;
import mobs.Player;

public class FetidGrotto extends Room {

	private int numItems;
	private int fatigue;
	
	public FetidGrotto(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Fetid Grotto";
		numItems = 3;
		fatigue = 2;
	}

	@Override
	public String getDescription() {
		return "Here it reeks of stagnant water and the things that dwell in it. But in the murky water... is that the glint of gold?";
	}

	@Override
	public String getCompletedDescription() {
		return "Here it reeks of stagnant water and the things that dwell in it.";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction dive = new RoomAction("Dive for Treasure") {
			@Override
			public String resolveAction(Player player) {
				if (player.isStrong()) {
					for (int i = 0; i < numItems; i++) {
						droppedItems.add(Item.getRandItemWeighted(floor.getFloorNumber()));
					}
					setCompleted(true);
					return "You dive into the water, navigating more by touch than by sight, and hoping something nasty doesn't "
							+ "try to have you for lunch. You find the unmistakable handle of a chest, and though it's stuck in "
							+ "the muck, you manage to pull it free and drag it from the bottom. You resurface and dump the contents "
							+ "on solid ground, gasping for air.";
				} else {
					player.takeFatigue(fatigue);
					return "You dive into the water and find a heavy chest, but you thrash under the water trying to free it from "
							+ "the grasping muck. Eventually you're forced to give in, gasping for breath on solid land. The effort "
							+ "has exhausted you, but once you've caught your breath, nothing is stopping you from trying again.";
				}
				
			}
		};
		
		roomActions.add(dive);
		
	}

}
