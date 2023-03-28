package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;

public class StoneCircle extends Room {

	private boolean isBlessed;
	private int buff;
	
	public StoneCircle(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Stone Circle";
		
		isBlessed = false;
		buff = 5;
	}

	@Override
	public String getDescription() {
		return "Your path leads up a hill shrouded in mist. At the top is a circle of monolithic standing stones made of granite, "
				+ "each inscribed with a variety of runes and pictograms. Whenever you near one, it glows softly.";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!isBlessed) {
			RoomAction blessing = new RoomAction("Obtain a Blessing") {
				@Override
				public String resolveAction(Player player) {
					isBlessed = true;
					String attribute = "";
					switch (GameScreen.generateRandom(1, 5)) {
					case 1:
						attribute += "Crit Rate";
						player.modBaseCritRate(buff);
						break;
					case 2:
						attribute += "Agility";
						player.modBaseAgility(buff);
						break;
					case 3:
						attribute += "Mind";
						player.modBaseMind(buff);
						break;
					case 4:
						attribute += "Strength";
						player.modBaseStrength(buff);
						break;
					case 5:
						attribute += "Toughness";
						player.modBaseToughness(buff);
						break;
					default:
					}
					
					return "You reach out and touch one of the standing stones. A sudden surge of energy washes over you, and "
							+ "you glow for a moment before it's suddenly over.\nYour " + attribute + " has been increased by "
							+ buff + ".";
				}
			};
			
			roomActions.add(blessing);
		}
		
	}

}
