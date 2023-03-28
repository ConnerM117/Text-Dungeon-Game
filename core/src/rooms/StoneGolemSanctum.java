package rooms;

import floors.Floor;
import mobs.Player;

import combat.CombatEncounter;
import combat.StoneGolemEncounter;
import core.Chest;

public class StoneGolemSanctum extends ScriptedCombatRoom {
	int numContents;
	
	public StoneGolemSanctum(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Stone Golem Sanctum";
		combatChance = 0;
		numContents = 3;
		hasChest = false;
		chest = new Chest(floor.getFloorNumber(), Chest.Type.UNLOCKED, numContents);
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new StoneGolemEncounter();
	}
	
	@Override
	public String getDescription() {
		return "The groaning of stone on stone echoes off the walls as you enter and see a monstrous figure. "
				+ "The statue seems docile for now, but it watches you intently with glowing eyes. It seems to "
				+ "be guarding the treasure present in this room; you may be able to avoid it, if you leave the "
				+ "treasure alone...";
	}
	
	@Override
	public String getCompletedDescription() {
		hasChest = true;
		return "The defeated stone golem lies toppled to the side, seemingly inert.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!isCompleted()) {
			RoomAction attack = new RoomAction("Attack!") {
				@Override
				public String resolveAction(Player player) {
					isAttacking = true;
					return "You draw your weapon and advance on the golem, whose eyes glow brighter. It rumbles as it "
							+ "raises its fists, ready to do battle!\n";
				}
			};
			
			roomActions.add(attack);
		}
		
	}

}
