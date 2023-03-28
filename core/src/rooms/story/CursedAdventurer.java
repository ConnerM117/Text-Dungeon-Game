package rooms.story;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.consumables.StolenGoods;
import mobs.Player;
import rooms.Room;

public class CursedAdventurer extends Room {

	private boolean hasSpoken;
	
	public CursedAdventurer(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Cursed Adventurer";
		combatChance = NO_COMBAT_CHANCE;
		hasSpoken = false;
	}

	@Override
	public String getDescription() {
		if (!hasSpoken)
			return "\"Hello?\" a weak voice says from the darkness of the stone tunnel. \"Is someone there?\"";
		else if (GameScreen.isStolenLootReturned)
			return "The adventurer's body- or what remained of it- is inert, one with the stone. Whatever happened, it appears that "
					+ "your act has allowed them to pass on.";
		return "\"Please return what I stole to the dragon's hoard,\" the thief says, \"That I may be free.\"";
	}

	@Override
	public String getCompletedDescription() {
		return "";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!hasSpoken) {
			RoomAction speak = new RoomAction("Speak") {
				@Override
				public String resolveAction(Player player) {
					GameScreen.setLogger("\"Over here,\" says the voice. You follow to a secluded corner, where lies an emaciated man whose "
							+ "skin seems to blend with the stone, as if he was carved from it.");
					hasSpoken = true;
					player.addToInventory(new StolenGoods());
					return "\"They all said it was cursed, but I wouldn't listen. I stole from the hoard, cursed gold! And since, "
							+ "nothing but misfortune has befallen me. I was so close, but even now I find no respite in death; "
							+ "only punishment. Please return this to the hoard, that I may be free.\"";
				}
			};
			
			roomActions.add(speak);
		}
	}

}
