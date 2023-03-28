package rooms.story;

import combat.CombatEncounter;
import combat.StoneGolemEncounter;
import floors.Floor;
import items.consumables.SmithingTools;
import mobs.Player;
import rooms.ScriptedCombatRoom;

public class RuinedWarehouse extends ScriptedCombatRoom {

	public RuinedWarehouse(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Ruined Warehouse";
		combatChance = NO_COMBAT_CHANCE;
		droppedItems.add(new SmithingTools());
	}

	@Override
	public CombatEncounter getCombatEncounter() {
		return new StoneGolemEncounter();
	}
	
	@Override
	public String getDescription() {
		return "A large stone building, reduced mostly to rubble, stands against a nearby cliff. A tall statue rummages through the "
				+ "debris; getting close enough to search the building would probably provoke it.";
	}

	@Override
	public String getCompletedDescription() {
		return "A large stone building, reduced mostly to rubble, stands against a nearby cliff.";
	}

	@Override
	public void initRoomActions(Player player) {
		if (!isCompleted()) {
			RoomAction attack = new RoomAction("Attack!") {
				@Override
				public String resolveAction(Player player) {
					isAttacking = true;
					return "";
				}
			};
			
			roomActions.add(attack);
		}
	}

}
