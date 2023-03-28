package floors;

import combat.TrollBossEncounter;
import combat.TrollEncounter;
import rooms.RockySlope;
import rooms.Room;
import rooms.TrollHoard;
import rooms.bosses.TrollLair;
import rooms.empty.Cavern;
import rooms.lairs.OgreDwelling;
import rooms.story.DarkCavern;
import rooms.story.HiddenCavern;

public class TrollCave extends Floor {
	
	public TrollCave(int floorNumber) {
		super(floorNumber);
		name = "Troll Cave";
		encounter = new TrollEncounter();
		bossEncounter = new TrollBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new OgreDwelling(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new TrollLair(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The dungeon halls turn to natural caverns lined with stalactites and stalagmites. "
				+ "Bones of larger creatures litter the floor- including what looks like human skulls. "
				+ "They've been picked clean. You wonder how many of them were adventurers like you...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new Cavern(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new DarkCavern(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new HiddenCavern(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new RockySlope(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new TrollHoard(roomNumber, this);
	}

}
