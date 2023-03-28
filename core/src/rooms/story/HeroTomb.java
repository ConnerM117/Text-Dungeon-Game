package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.weapons.Weapon;
import mobs.Player;
import rooms.Room;

public class HeroTomb extends Room {

	private final String DESCRIPTION = "A beam of light shines down from a hole high in the wall; you're deep underground, "
			+ "so the light must be magical. It falls directly onto a stone sarcophagus and illuminates the otherwise empty room.";
	
	private boolean hasSpoken;
	
	public HeroTomb(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Hero's Tomb";
		combatChance = NO_COMBAT_CHANCE;
		type = Type.STORY;
		hasSpoken = false;		
	}
	
	@Override
	public String getDescription() {
		if (hasSpoken)
			return DESCRIPTION + " A ghostly voice says, \"Avenge me... kill the wraith!\"";
		else
			return DESCRIPTION + " A ghostly voice calls, \"Who goes?\"";
	}

	@Override
	public String getCompletedDescription() {
		return DESCRIPTION + " The ghostly presence has gone.";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Answer") {
				@Override
				public String resolveAction(Player player) {
					hasSpoken = true;
					return "After hearing your voice, an apparition of an adventurer appears before you, sitting on the sarcophagus. "
							+ "\"I was defeated,\" he says, \"By the wraith that resides in the Haunted Tomb. My companions entombed "
							+ "me here, but I cannot find rest while the fiend lives. Destroy the wraith, and I will ensure you are "
							+ "rewarded.\"";
				}
			};
			
			roomActions.add(speak);
		}
		
		if (GameScreen.wraithIsSlain && hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					setCompleted(true);
					droppedItems.add(Weapon.getRandWeaponWithRune(floor.getFloorNumber()));
					return "\"I sense the wraith's departure,\" the ghost says. \"Thank you. Now I can find peace. Here.\" "
							+ "He draws his spectral weapon and lays it on the sarcophagus. After a moment, it shimmers and "
							+ "becomes real. \"You will need it more than I will,\" he says. Then he bows his head and fades.";
				}
			};
			
			roomActions.add(speak);
		}
		
	}

}
