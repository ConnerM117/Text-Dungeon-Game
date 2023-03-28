package floors;

import combat.DungeonHeartEncounter;
import combat.EldritchEncounter;
import rooms.*;
import rooms.bosses.DungeonHeart;
import rooms.empty.StarryExpanse;
import rooms.lairs.EldritchGate;

public class AstralDominion extends Floor {
	
	public AstralDominion(int floorNumber) {
		super(floorNumber);
		name = "Astral Dominion";
		encounter = new EldritchEncounter();
		bossEncounter = new DungeonHeartEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new EldritchGate(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new DungeonHeart(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "There is something wrong with this place. Matter seems to warp around you. And are "
				+ "those stars you see? The longer you stare, the more wrong it seems. At least the "
				+ "way forward seems clear...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new StarryExpanse(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new RelicWeaponRoom(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new RelicWeaponRoom(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new PortalRoom(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new EndlessHallway(roomNumber, this);
	}

}
