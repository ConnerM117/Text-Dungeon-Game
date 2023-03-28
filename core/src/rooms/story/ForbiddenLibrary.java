package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.consumables.RitualTome;
import mobs.Player;
import rooms.Room;

public class ForbiddenLibrary extends Room {

	private boolean hasRead;
	private int curseMod;
	
	public ForbiddenLibrary(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Forbidden Library";
		combatChance = NO_COMBAT_CHANCE;
		hasRead = false;
		curseMod = 2;
		addDroppedItem(new RitualTome());
	}

	@Override
	public String getDescription() {
		return "The ceiling here is low, and enormous tomes are held into spindly bookshelves with black chains. Whatever knowledge "
				+ "is contained here is undoubtedly dangerous.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!hasRead) {
			RoomAction read = new RoomAction("Read") {
				@Override
				public String resolveAction(Player player) {
					player.modCurse(curseMod);
					GameScreen.isForbiddenTomeRead = true;
					return "\"One of the tomes catches your eye. The knowledge it contains is dark and powerful, almost like it "
							+ "threatens to take hold of you and never let go. You know more about these demons and the rituals "
							+ "involved, but at what cost?";
				}
			};
			
			roomActions.add(read);
		}
	}

}
