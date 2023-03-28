package floors;

import combat.RatBossEncounter;
import combat.RatEncounter;
import rooms.Labyrinth;
import rooms.MushroomPatch;
import rooms.Room;
import rooms.bosses.RatKingThrone;
import rooms.empty.DungeonEntrance;
import rooms.empty.WindingTunnel;
import rooms.lairs.RatNest;
import rooms.story.AlchemyWorkshop;
import rooms.story.MushroomGarden;

public class RatWarrens extends Floor {
	
	public RatWarrens(int floorNumber) {
		super(floorNumber);
		name = RAT_WARRENS;
		encounter = new RatEncounter();
		bossEncounter = new RatBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new RatNest(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new RatKingThrone(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "You walk into the dark and come to a part of the dungeon made up of a labyrinth of "
				+ "cramped tunnels that branch every which way. There are even more tunnels too small "
				+ "for you to use; the dungeon denizens must use them to go where they please...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		if (roomNumber == 1) {
			return new DungeonEntrance(roomNumber, this);
		}
		return new WindingTunnel(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new AlchemyWorkshop(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new MushroomGarden(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new Labyrinth(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new MushroomPatch(roomNumber, this);
	}

}
