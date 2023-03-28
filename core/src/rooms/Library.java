package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.*;

public class Library extends Room {
	
	private boolean hasRead;
	private int mindBuff;
	
	public Library(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Library";
		
		hasRead = false;
		mindBuff = 5;
	}
	
	@Override
	public String getDescription() {
		return "The walls are lined with bookshelves, themselves lined with rows of dusty old tomes.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!hasRead) {
			RoomAction read = new RoomAction("Read") {
				@Override
				public String resolveAction(Player player) {
					hasRead = true;
					switch (GameScreen.generateRandom(1, 3)) {
					case 1: //dungeon map
						floor.getFloorRooms().forEach(room -> room.setScouted(true));
						return "You peruse the manuals for a bit, but then a table with parchments spread on it catches your "
								+ "eye. You study them and find that they are maps of the surrounding dungeon in great detail!";
					case 2:
						player.modBaseMind(mindBuff);
						return "You pick out an interesting-looking volume. You don't read much, but what you do read is "
								+ "particularly enlightening.";
					case 3:
						return "None of the volumes are particularly interesting. You don't have the time to read them, anyway.";
					}
					return "";
				}
			};
			
			roomActions.add(read);
		}
	}
}
