package floors;

import combat.GoblinBossEncounter;
import combat.GoblinEncounter;
import rooms.Labyrinth;
import rooms.MushroomPatch;
import rooms.Room;
import rooms.bosses.GoblinKingThrone;
import rooms.empty.WindingTunnel;
import rooms.lairs.GoblinDen;
import rooms.story.GoblinGuardRoom;
import rooms.story.GoblinPrison;

public class GoblinTunnels extends Floor {

	public GoblinTunnels(int floorNumber) {
		super(floorNumber);
		name = "Goblin Tunnels";
		encounter = new GoblinEncounter();
		bossEncounter = new GoblinBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new GoblinDen(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new GoblinKingThrone(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "This section of the dungeon is composed of many overlapping tunnels, and the floor "
				+ "is strewn with feces and the bones of small mammals. This and the stench are more than "
				+ "enough to prove to you that there are multitudes of goblins prowling about...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new WindingTunnel(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new GoblinPrison(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new GoblinGuardRoom(roomNumber, this);
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
