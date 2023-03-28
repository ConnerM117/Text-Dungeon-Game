package rooms.story;

import floors.Floor;
import items.consumables.AlchemySupplies;
import mobs.Player;
import rooms.Room;

public class RuinedOutpost extends Room {

	public RuinedOutpost(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ruined Outpost";
		combatChance = NO_COMBAT_CHANCE;
	}
	
	@Override
	public String searchRoom(Player player) {
		String str = super.searchRoom(player);
		str += "\nWithin the building lies a dessicated corpse clutching a notebook and a sack of supplies.";
		return str;
	}

	@Override
	public String getDescription() {
		return "The remains of a small stone building protrude from the swamp. Part of it is intact; there may be something valuable inside.";
	}

	@Override
	public String getCompletedDescription() {
		return "The remains of a small stone building protrude from the swamp. Part of it is intact.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (isSearched) {
			RoomAction searchBody = new RoomAction("Investigate Corpse") {
				@Override
				public String resolveAction(Player player) {
					setCompleted(true);
					player.addToInventory(new AlchemySupplies());
					return "You pry the book and sack away from the remains and take a closer look at each. The book seems to detail "
							+ "alchemical recipes of several varieties, though the only one that's completely legible is the one at the "
							+ "end. The sack holds equipment that one would need to create such recipes.";
				}
			};
			
			roomActions.add(searchBody);
		}
		
	}

}
