package rooms.lairs;

import floors.Floor;
import mobs.*;
import rooms.Room;

public class DemonShrine extends Room {
	
	private int XPMod;
	
	public DemonShrine(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Demon Shrine";
		XPMod = 2;
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String getDescription() {
		return "A makeshift shrine is built in this room, covered in blasphemous runes that glow red, and surrounded by rings of "
				+ "lit candles that cast flickering shadows across the walls.";
	}

	@Override
	public String getCompletedDescription() {
		return "A makeshift shrine is built in this room, though the runes scrawled across it no longer glow and the candles have "
				+ "gone out. Its power is spent.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!isCompleted()) {
			RoomAction ritual = new RoomAction("Perform a Ritual") {
				@Override
				public String resolveAction(Player player) {
					floor.modifyXPLimit(XPMod);
					return "The candles and runes flare, bathing the room in a ghastly red glow, before completely going out. You're "
							+ "left in darkness, somehow knowing that the demonic grip in the dungeon has been strengthened.";
				}
			};
			
			roomActions.add(ritual);
			
			if (player.isHoly()) {
				RoomAction bless = new RoomAction("Dispel Evil") {
					@Override
					public String resolveAction(Player player) {
						if (player.getCurrentStamina() > 0) {
							floor.modifyXPLimit(-XPMod);
							setCompleted(true);
							return player.spendStamina(1) + "\nYou utter a blessing on this place and the runes fade as the candles blow out, "
									+ "leaving trailing wisps of smoke. The demonic grip in the dungeon has been weakened. For now, at least.";
						} else {
							return "You don't have enough Stamina to perform this action.";
						}
					}
				};
				
				roomActions.add(bless);
			}
		}
		
	}

}
