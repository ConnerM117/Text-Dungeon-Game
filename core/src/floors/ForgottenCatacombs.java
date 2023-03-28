package floors;

import com.textdungeon.game.GameScreen;

import combat.SkeletonBossEncounter;
import combat.SkeletonEncounter;
import rooms.Room;
import rooms.StoneGolemSanctum;
import rooms.TrappedChest;
import rooms.bosses.BoneCrypt;
import rooms.empty.LongHallway;
import rooms.empty.RubbleRoom;
import rooms.lairs.SkeletonTomb;
import rooms.story.HauntedTomb;
import rooms.story.HeroTomb;

public class ForgottenCatacombs extends Floor {
	
	public ForgottenCatacombs(int floorNumber) {
		super(floorNumber);
		name = FORGOTTEN_TOMB;
		encounter = new SkeletonEncounter();
		bossEncounter = new SkeletonBossEncounter();
	}
	
	public Room getLairRoom(int roomNumber) { return new SkeletonTomb(roomNumber, this); }
	
	public Room getBossRoom(int roomNumber) { return new BoneCrypt(roomNumber, this); }
	
	@Override
	public String getDescription() {
		return "The air here is musty and stale. Dust coats almost every surface, and cobwebs hang "
				+ "in the stone halls. It isn't long before you find the first of many stone sepulchers, "
				+ "but it is empty. Something tells you the dead here may not be sleeping so peacefully...";
	}

	@Override
	protected Room getEmptyRoom(int roomNumber) {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new LongHallway(roomNumber, this);
		else
			return new RubbleRoom(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomOne(int roomNumber) {
		return new HeroTomb(roomNumber, this);
	}

	@Override
	protected Room getStoryRoomTwo(int roomNumber) {
		return new HauntedTomb(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomOne(int roomNumber) {
		return new TrappedChest(roomNumber, this);
	}

	@Override
	protected Room getUniqueRoomTwo(int roomNumber) {
		return new StoneGolemSanctum(roomNumber, this);
	}

}
