package floors;

import com.textdungeon.game.GameScreen;

import combat.UndeadBossEncounter;
import combat.UndeadEncounter;
import items.consumables.ArcaneText;
import rooms.Library;
import rooms.Observatory;
import rooms.Room;
import rooms.bosses.LichLair;
import rooms.empty.LongHallway;
import rooms.lairs.UndeadOutpost;
import rooms.story.ArcaneStudy;
import rooms.story.GuardedSanctum;

public class LichDomain extends Floor {
	
	public LichDomain(int floorNumber) {
		super(floorNumber);
		name = "Lich's Domain";
		encounter = new UndeadEncounter();
		bossEncounter = new UndeadBossEncounter();
		floorRooms.get(GameScreen.generateRandom(0, floorRooms.size()-INVALID_ROOMS)).addDroppedItem(new ArcaneText());
	}
	
	public Room getLairRoom(int roomNumber) { return new UndeadOutpost(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new LichLair(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "You pass into an area of the dungeon that reeks of death. Despite this, the place "
				+ "seems bizarrely well-kept, perhaps the work of the restless dead...";
	}
	
	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new LongHallway(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new ArcaneStudy(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new GuardedSanctum(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new Library(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new Observatory(roomNumber, this);
	}

}
