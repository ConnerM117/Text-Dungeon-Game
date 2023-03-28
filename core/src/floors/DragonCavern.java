package floors;

import combat.DragonBossEncounter;
import combat.DragonEncounter;
import rooms.LavaRiver;
import rooms.Ravine;
import rooms.Room;
import rooms.bosses.DragonLair;
import rooms.empty.Cavern;
import rooms.lairs.DragonNest;
import rooms.story.CursedAdventurer;
import rooms.story.DragonHoard;

public class DragonCavern extends Floor {

	
	public DragonCavern(int floorNumber) {
		super(floorNumber);
		name = DRAGON_CAVERN;
		encounter = new DragonEncounter();
		bossEncounter = new DragonBossEncounter();
	}

	public Room getLairRoom(int roomNumber) { return new DragonNest(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new DragonLair(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The air becomes hot and stuffy, and up ahead the dungeon is lit by an eerie red glow. "
				+ "Gouges mark where enormous claws have marred the stone...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new Cavern(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new CursedAdventurer(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new DragonHoard(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new Ravine(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new LavaRiver(roomNumber, this);
	}

}
