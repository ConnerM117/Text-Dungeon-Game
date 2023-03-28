package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import mobs.Player;

public class Obelisk extends Room {

	private int fatigue;
	private int XP;
	private boolean isCorrupted;
	private String runeColor;
	private int curseMod;
	
	public Obelisk(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ancient Obelisk";
		fatigue = 3;
		XP = 3;
		isCorrupted = initCorrupted();
		curseMod = 1;
		
		if (isCorrupted)
			runeColor = "red";
		else
			runeColor = "gold";
	}

	@Override
	public String getDescription() {
		return "A wide monolith comes into sight, its surface black and shining like polished obsidian. Ancient runes carved on its "
				+ "surface glow " + runeColor + ", pulsing like a heartbeat concealed within. You feel a strong urge to touch it.";
	}

	@Override
	public String getCompletedDescription() {
		return "A wide monolith stands alone, its surface black and shining like polished obsidian. Ancient runes carved on its "
				+ "surface glow " + runeColor + ", though their glow has faded some.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction readRunes = new RoomAction("Study the Runes") {
			@Override
			public String resolveAction(Player player) {
				return "The runes are not in a language you recognize; in fact, they are utterly incomprehensible, and seem to "
						+ "shift if you look at them for too long.";
			}
		};
		
		RoomAction touchObelisk = new RoomAction("Touch the Obelisk") {
			@Override
			public String resolveAction(Player player) {
				if (isCorrupted) {
					String str = "You reach out and touch the obelisk. Immediately your mind is taken by visions. When "
							+ "all is done you can't remember anything you saw, but a dark feeling has a hold on you that "
							+ "refuses to release. This thing is integrally connected to the dungeon, "
							+ "but apart from that its function and purpose is a mystery...";
					str += "\n" + player.takeFatigue(fatigue);
					str += "\n" + player.receiveXP(XP);
					player.modCurse(curseMod);
					setCompleted(true);
					return str;
				} else {
					String str = "You reach out and touch the obelisk. Immediately your mind is taken by visions, though when "
							+ "all is done you can't remember anything you saw. This thing is integrally connected to the dungeon, "
							+ "but apart from that its function and purpose is a mystery...";
					str += "\n" + player.takeFatigue(fatigue);
					str += "\n" + player.receiveXP(XP);
					setCompleted(true);
					return str;
				}
			}
		};
		
		roomActions.add(readRunes);
		roomActions.add(touchObelisk);
	}
	
	private boolean initCorrupted() {
		if (GameScreen.generateRandom(1, 3) == 1)
			return true;
		return false;
	}

}
