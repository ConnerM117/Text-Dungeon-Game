package floors;

import com.textdungeon.game.GameScreen;

import combat.DemonBossEncounter;
import combat.DemonEncounter;
import rooms.CursedPrison;
import rooms.Room;
import rooms.SummoningPentagram;
import rooms.bosses.TempleSanctum;
import rooms.empty.LongHallway;
import rooms.empty.RubbleRoom;
import rooms.lairs.DemonShrine;
import rooms.story.BoundDemon;
import rooms.story.ForbiddenLibrary;

public class DesecratedTemple extends Floor {
	
	public DesecratedTemple(int floorNumber) {
		super(floorNumber);
		name = "Desecrated Temple";
		encounter = new DemonEncounter();
		bossEncounter = new DemonBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new DemonShrine(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new TempleSanctum(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The dungeon halls turn to natural caverns lined with stalactites and stalagmites. "
				+ "Bones of larger creatures litter the floor- including what looks like human skulls. "
				+ "They've been picked clean. You wonder how many of them were adventurers like you...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		if (GameScreen.generateRandom(1, 2) == 1)
			return new RubbleRoom(roomNumber, this);
		return new LongHallway(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new BoundDemon(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new ForbiddenLibrary(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new SummoningPentagram(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new CursedPrison(roomNumber, this);
	}

}
