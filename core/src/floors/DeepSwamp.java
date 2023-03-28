package floors;

import combat.SwampBossEncounter;
import combat.SwampEncounter;
import rooms.FetidGrotto;
import rooms.Quicksand;
import rooms.Room;
import rooms.bosses.HydraLair;
import rooms.empty.SecludedGrotto;
import rooms.lairs.LizardmanHovel;
import rooms.story.RuinedOutpost;
import rooms.story.RuinedWorkshop;

public class DeepSwamp extends Floor {
	
	public DeepSwamp(int floorNumber) {
		super(floorNumber);
		name = "Deep Swamp";
		encounter = new SwampEncounter();
		bossEncounter = new SwampBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new LizardmanHovel(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new HydraLair(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The stone dungeon gives way to mud and marshy water, with enormous growths of fungi "
				+ "resembling trees and other above-ground foliage. Each cavern is this way, and deeper "
				+ "in you can hear rushing water...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new SecludedGrotto(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new RuinedOutpost(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new RuinedWorkshop(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new FetidGrotto(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new Quicksand(roomNumber, this);
	}

}
