package rooms.story;

import mobs.Player;
import rooms.Room;

import com.textdungeon.game.GameScreen;

import floors.Floor;

public class GoblinPrison extends Room {

	public static final String PRISON_KEY = "PRISON KEY";
	
	public GoblinPrison(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Goblin Prison";
		combatChance = NO_COMBAT_CHANCE;
		type = Type.STORY;
	}
	
	@Override
	public String getDescription() {
		return "A torch casts flickering light over prison cells, most of which are empty but for bits of bones and straw. "
				+ "One of them is occupied with a muscular man who presses himself to the bars and says, \"Please, help me!\"";
	}

	@Override
	public String getCompletedDescription() {
		return "A torch casts flickering light over prison cells, which are empty but for bits of bones and straw.";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction speak = new RoomAction("Speak") {
			@Override
			public String resolveAction(Player player) {
				return "\"Please, get me out of here!\" the man says. \"The goblin guards keep a " + PRISON_KEY + " in "
						+ "the GUARD ROOM. If you can get me out, I promise you won't regret it!";
			}
		};
		
		roomActions.add(speak);
		
		if (player.getKeyRing().contains(PRISON_KEY)) {
			RoomAction free = new RoomAction("Free Prisoner") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.isBlacksmithFreed = true;
					setCompleted(true);
					return "\"Thank you!\" he exclaims, scrambling out of the cell. \"I promise I will make it up to you! "
							+ "If you find me in the " + Floor.DEEP_FOREST + ", I can improve your weapons and armor!";
				}
			};
			
			roomActions.add(free);
		}
		
	}

}
