package floors;

import combat.ForestBossEncounter;
import combat.ForestEncounter;
import rooms.Room;
import rooms.StoneCircle;
import rooms.Waterfall;
import rooms.bosses.DruidTower;
import rooms.empty.ForestClearing;
import rooms.lairs.ForestGlade;
import rooms.story.RuinedWarehouse;
import rooms.story.Smithy;

public class DeepForest extends Floor {
	
	public DeepForest(int floorNumber) {
		super(floorNumber);
		name = "Deep Forest";
		encounter = new ForestEncounter();
		bossEncounter = new ForestBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new ForestGlade(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new DruidTower(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The dungeon gives way for massive caverns so tall you can't see the top. And is that "
				+ "natural light coming through massive trees? And grass on the ground? You don't have "
				+ "much time to wonder how it got here, as you can hear wolves howling in the distance...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		return new ForestClearing(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new Smithy(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new RuinedWarehouse(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new StoneCircle(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new Waterfall(roomNumber, this);
	}
}
