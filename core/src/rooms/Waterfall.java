package rooms;

import java.util.HashSet;
import java.util.Set;

import floors.Floor;
import mobs.Player;
import rooms.story.Smithy;

public class Waterfall extends Room {

	private enum Location {
		POOL, CAVE
	}
	
	Location location;
	private boolean secretDiscovered;
	private boolean doorIsOpen;
	private Set<Room> caveAdjacentRooms;
	
	public Waterfall(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Waterfall";
		location = Location.POOL;
		secretDiscovered = false;
		doorIsOpen = false;
		caveAdjacentRooms = new HashSet<>();
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		secretDiscovered = true;
		str += "\nAfter taking a closer look, you realize that there is a cave hidden behind the waterfall!";
		return str;
	}

	@Override
	public String getDescription() {
		return "A picturesque waterfall flows over a tall cliff and into a roiling pool directly in front of you. Trees and foliage "
				+ "surrounding the pool make this a serene and somewhat private place.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (secretDiscovered && location == Location.POOL) {
			RoomAction enterCave = new RoomAction("Enter the Cave") {
				@Override
				public String resolveAction(Player player) {
					location = Location.CAVE;
					return "You swim across the pool and pull yourself up into the cave. It's dark and wet in here, but that doesn't "
							+ "stop you from getting a good look around. There's an odd-looking door at the back, shaped like an "
							+ "enormous cog, with a keyhole in the center.";
				}
			};
			
			roomActions.add(enterCave);
		}
		
		if (location == Location.CAVE) {
			RoomAction exitCave = new RoomAction("Exit the Cave") {
				@Override
				public String resolveAction(Player player) {
					location = Location.POOL;
					return "You dive into the pool and swim across to the other side before pulling yourself up and out.";
				}
			};
			
			RoomAction investigateDoor = new RoomAction("Investigate Door") {
				@Override
				public String resolveAction(Player player) {
					return "The door is shaped like an enormous cog, and has what looks like a keyhole in the center. It's "
							+ "covered in writing you don't recognize, but many of the symbols seem to have something to do "
							+ "with machinery.";
				}
			};
			
			roomActions.add(exitCave);
			roomActions.add(investigateDoor);
			
			if (player.getKeyRing().contains(Smithy.CLOCKWORK_KEY) && !doorIsOpen) {
				RoomAction tryKey = new RoomAction("Use Clockwork Key") {
					@Override
					public String resolveAction(Player player) {
						doorIsOpen = true;
						//TODO add the first room of Clockwork Forge to caveAdjacentRooms
						return "The " + Smithy.CLOCKWORK_KEY + " fits and turns. You hear metal grating against metal, and then the "
								+ "door spins before swinging open.";
					}
				};
				
				roomActions.add(tryKey);
			}
		}
	}
	
	@Override
	public Set<Room> getAdjacentRooms() {
		switch (location) {
		case CAVE:
			return caveAdjacentRooms;
		case POOL:
			return adjacentRooms;
		default:
			return adjacentRooms;
		
		}
	}

}
