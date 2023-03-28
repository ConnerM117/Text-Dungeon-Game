package rooms.lairs;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import mobs.*;
import rooms.Room;

public class EldritchGate extends Room {

	public EldritchGate(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Eldritch Gate";
		combatChance = LAIR_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nYou find something. Or... does it find you?\n";
		if (GameScreen.generateRandom(1, 2) == 1)
			str += player.addToInventory(Item.getRandItemWeighted(floor.getFloorNumber()));
		else 
			str += player.addToInventory(Item.getEldritchItem());
		return str;
	}
	
	@Override
	public String getDescription() {
		return "A low hum fills the air. The veil is thin here. It's like you could reach out and touch the stars."
				+ "\nBut no. They would sense you. The thought nearly fills you with panic, but you regain control. Now is no time for panic."
				+ "\n...Though surely they are coming.";
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
