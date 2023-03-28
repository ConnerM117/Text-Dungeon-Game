package rooms.bosses;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class DungeonHeart extends Room {
	
	public DungeonHeart(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "The Heart of the Dungeon";
		isBossRoom = true;
		combatChance = NO_COMBAT_CHANCE;
		type = Type.BOSS;
	}
	
	@Override
	public String getDescription() {
		return "All you can hear is a high-pitched tone. You see galaxies full of stars. Are you "
				+ "even underground anymore? Things seem slower- but faster. You're lighter and heavier "
				+ "at once. Something appears in front of you- or has it always been there? It's a "
				+ "shapless mass, but as it comes in and out of focus you can make out an arm here, "
				+ "a face there, and you suddenly realize that these are the doomed adventurers that "
				+ "met their fate within the dungeon. A warbling voice resonates through you, "
				+ "demanding that you hear and answer. \"COME TO ME, MY "
				+ "PRIZE. COME TO ME, AND AT LAST WE SHALL BE ONE.\"";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		// no special actions
	}

}
