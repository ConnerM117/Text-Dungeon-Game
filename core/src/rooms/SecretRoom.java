package rooms;

import core.Chest;
import floors.Floor;
import items.Item;
import mobs.Player;

public class SecretRoom extends Room {

	private int chestContents;
	
	public SecretRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Secret Room";
		hasChest = true;
		chestContents = 3;
		chest = new Chest(floor.getFloorNumber(), Chest.Type.UNLOCKED, chestContents);
		droppedItems.add(Item.getRandSpecialItem());
		droppedItems.add(Item.getRandItemWeighted(floor.getFloorNumber()));
		droppedItems.add(Item.getRandItemWeighted(floor.getFloorNumber()));
	}
	
	@Override
	public String getDescription() {
		return "This area is sealed off from the rest of the dungeon, and apparently has been for some time.";
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
