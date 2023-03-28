package rooms.story;

import combat.CombatEncounter;
import combat.GuardedSanctumEncounter;
import floors.Floor;
import items.consumables.EvilArtifact;
import mobs.Player;
import rooms.ScriptedCombatRoom;
import traps.CollapsingTrap;

public class GuardedSanctum extends ScriptedCombatRoom {

	public GuardedSanctum(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Guarded Sanctum";
		type = Type.STORY;
		combatChance = NO_COMBAT_CHANCE;
		droppedItems.add(new EvilArtifact());
		
		hasTrap = true;
		trap = new CollapsingTrap();
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new GuardedSanctumEncounter();
	}

	@Override
	public String getDescription() {
		return "Two stone golems guard the entrance to the this room, wherein you can see an artifact lying on a pedestal. There's "
				+ "no chance you'll obtain it without fighting them.";
	}

	@Override
	public String getCompletedDescription() {
		return "Two stone golems lie to the sides, defeated. The pedestal where the artifact lay is empty.";
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
