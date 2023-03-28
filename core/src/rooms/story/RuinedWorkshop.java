package rooms.story;

import floors.Floor;
import items.consumables.AlchemySupplies;
import items.consumables.BurningPitch;
import mobs.Player;
import rooms.Room;

public class RuinedWorkshop extends Room {

	private String alchemyTools;
	private int alchemyDoses;
	
	public RuinedWorkshop(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ruined Workshop";
		combatChance = NO_COMBAT_CHANCE;
		alchemyTools = new AlchemySupplies().getName();
		alchemyDoses = 3;
	}

	@Override
	public String getDescription() {
		return "Most of this building is destroyed, but a large pantry of alchemical ingredients appears to have survived unharmed. "
				+ "If you only you had the tools and knowledge to put them to use.";
	}

	@Override
	public String getCompletedDescription() {
		return "Most of this building is destroyed, but a large pantry of alchemical ingredients appears to have survived unharmed. "
				+ "It's a pity you can't read the other recipes in the book.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (player.getInventory().containsKey(alchemyTools)) {
			RoomAction create = new RoomAction("Follow Recipe") {
				@Override
				public String resolveAction(Player player) {
					player.removeFromInventory(alchemyTools);
					for (int i = 0; i < alchemyDoses; i++) {
						player.addToInventory(new BurningPitch());
					}
					setCompleted(true);
					return "You set up the tools as directed by the book, and follow the recipe to the best of your ability. The result "
							+ "is " + alchemyDoses + " doses of Burning Pitch!";
				}
			};
			
			roomActions.add(create);
		}
		
	}

}
