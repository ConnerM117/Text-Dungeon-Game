package rooms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;

public abstract class SplitRoom extends Room {

	private final String TREASURE_ACROSS = "\nYou can see something on the other side. Treasure perhaps?";
	
	protected enum RoomSide {
		SIDE_ONE, SIDE_TWO, MIDDLE //middle does not allow for any items or leaving the room
	}

	RoomSide roomSide;
	RoomSide initialSide;
	RoomSide oppositeSide;

	protected String sideOneDescription;
	protected String sideTwoDescription;
	protected Set<Room> sideOneRooms;
	protected Set<Room> sideTwoRooms;
	protected Set<Room> dummySet;
	protected List<Item> droppedItemsSideOne;
	protected List<Item> droppedItemsSideTwo;
	protected List<Item> droppedItemsMiddle;

	public SplitRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		roomSide = RoomSide.SIDE_ONE;
		droppedItemsSideOne = new ArrayList<>();
		droppedItemsSideTwo = new ArrayList<>();
		droppedItemsMiddle = new ArrayList<>();
		sideOneRooms = new HashSet<>();
		sideTwoRooms = new HashSet<>();
		dummySet = new HashSet<>();
	}

	@Override
	public void enterRoom() {
		if (sideTwoRooms.size() < 1) { // only initialize them the first time this method is called
			initSideRoomSets();
			initSideItemLists();
		}
		
		if (sideOneRooms.contains(GameScreen.previousRoom)) {
			roomSide = RoomSide.SIDE_ONE;
		} else {
			roomSide = RoomSide.SIDE_TWO;
		}
		initialSide = roomSide;
		
		switch (initialSide) {
		case SIDE_ONE:
			oppositeSide = RoomSide.SIDE_TWO;
			break;
		case SIDE_TWO:
			oppositeSide = RoomSide.SIDE_ONE;
			break;
		default:
			break;
		}
	}
	
	@Override
	public String getDescription() {
		if (roomSide == RoomSide.SIDE_ONE) {
			if (droppedItemsSideTwo.size() > 0) {
				return sideOneDescription + TREASURE_ACROSS;
			}
			return sideOneDescription;
		} else {
			if (droppedItemsSideOne.size() > 0) {
				return sideTwoDescription + TREASURE_ACROSS;
			}
			return sideTwoDescription;
		}
	}

	@Override
	public Set<Room> getAdjacentRooms() {
		switch (roomSide) {
		case SIDE_ONE:
			return sideOneRooms;
		case SIDE_TWO:
			return sideTwoRooms;
		default:
			return dummySet;
		}
	}

	@Override
	public List<Item> getItems() {
		switch (roomSide) {
		case SIDE_ONE:
			return droppedItemsSideOne;
		case SIDE_TWO:
			return droppedItemsSideTwo;
		default:
			return droppedItemsMiddle;
		}
	}

	@Override
	public void addDroppedItem(Item item) {
		droppedItems.add(item);
		switch (roomSide) {
		case SIDE_ONE:
			droppedItemsSideOne.add(item);
			break;
		case SIDE_TWO:
			droppedItemsSideTwo.add(item);
			break;
		default:
			droppedItemsMiddle.add(item);
			break;
		}
	}

	@Override
	public void removeDroppedItem(Item item) {
		droppedItems.remove(item);
		switch (roomSide) {
		case SIDE_ONE:
			droppedItemsSideOne.remove(item);
			break;
		case SIDE_TWO:
			droppedItemsSideTwo.remove(item);
			break;
		default:
			droppedItemsMiddle.remove(item);
			break;
		}

	}

	private void initSideRoomSets() {
		int counter = 1;
		for (Room r : adjacentRooms) {
			if (counter % 2 == 0) { // add every even one to side one
				sideOneRooms.add(r);
			} else { // add every odd one to side two
				sideTwoRooms.add(r);
			}
			counter++;
		}
	}

	private void initSideItemLists() {
		for (Item item : droppedItems) {
			if (droppedItemsSideOne.size() > droppedItemsSideTwo.size()) {
				droppedItemsSideTwo.add(item);
			} else {
				droppedItemsSideOne.add(item);
			}
		}
	}

	protected void switchSide() {
		switch (roomSide) {
		case SIDE_ONE:
			roomSide = RoomSide.SIDE_TWO;
			break;
		case SIDE_TWO:
			roomSide = RoomSide.SIDE_ONE;
			break;
		default:
			break;
		}
	}

}
