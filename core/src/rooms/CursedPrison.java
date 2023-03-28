package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;

public class CursedPrison extends Room {

	private boolean hasStudied;
	
	public CursedPrison(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Cursed Prison";
		hasStudied = false;
	}

	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		return str + "\nYou take the time to look in each cell. One of them is covered in demonic runes, seemingly drawn in blood.";
	}
	
	@Override
	public String getDescription() {
		if (isSearched())
			return "This place used to be a prison, but the iron doors have been torn free from their hinges, and enormous claw "
				+ "and scorch marks have marred the stone. Demonic runes drawn in blood cover the walls and floor of one particular cell. "
				+ "If you had a reference, perhaps you could try to study them.";
		return "This place used to be a prison, but the iron doors have been torn free from their hinges, and enormous claw "
				+ "and scorch marks have marred the stone.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (isSearched() && !hasStudied && player.getInventory().containsKey(SummoningPentagram.RITUAL_TOME)) {
			RoomAction studyRunes = new RoomAction("Study the Runes") {
				@Override
				public String resolveAction(Player player) {
					hasStudied = true;
					GameScreen.isCursedPrisonStudied = true;
					return "The runes are undoubtedly demonic, and they seem to detail a ritual of some kind. A few rituals, actually. "
							+ "And... something about contracts?";
				}
			};
			
			roomActions.add(studyRunes);
		}
	}

}
