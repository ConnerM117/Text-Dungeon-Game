package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;
import rooms.Room;

public class DarkCavern extends Room {

	private boolean hasSpoken;
	
	public DarkCavern(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Dark Cavern";
		combatChance = NO_COMBAT_CHANCE;
		hasSpoken = false;
	}

	@Override
	public String getDescription() {
		if (hasSpoken)
			return "Light is scarce here, and you hear someone move among the stone as you approach. \"Have you decided to help?\" he pleads.";
		return "Light is scarce here, and you hear something move among the stone, though you can't see it. A terrified voice whispers "
				+ "\"Hello? Who's there?\"";
	}

	@Override
	public String getCompletedDescription() {
		return "Light is scarce here. It's surely a good place to hide for a while- presuming those looking for you can't see in the dark.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					hasSpoken = true;
					return "\"Oh good, I thought you were a troll!\" a man says, emerging from the darkness. He's dressed in robes that suggest "
							+ "he's a wizard. \"I came down here with my master to harvest ingredients for Troll Brew, but we got separated. "
							+ "I've been to afraid to try to find him on my own. Can you help me?\"";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (hasSpoken && !isCompleted()) {
			RoomAction help = new RoomAction("Offer Help") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.isEscortingWizard = true;
					setCompleted(true);
					return "\"Oh thank you! I promise I won't be a burden. Though... I won't be much help either, I'm still only an "
							+ "apprentice.\"";
				}
			};
			
			roomActions.add(help);
			
			RoomAction notNow = new RoomAction("Not Now") {
				@Override
				public String resolveAction(Player player) {
					return "His face falls. \"Well, let me know if you change your mind...\"";
				}
			};
			
			roomActions.add(notNow);
		}
		
	}

}
