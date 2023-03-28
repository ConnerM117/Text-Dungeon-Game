package rooms.story;

import combat.CombatEncounter;
import combat.WraithEncounter;
import floors.Floor;
import mobs.Player;
import rooms.ScriptedCombatRoom;

public class HauntedTomb extends ScriptedCombatRoom {

	public HauntedTomb(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Haunted Tomb";
		combatChance = NO_COMBAT_CHANCE;
		type = Type.STORY;
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new WraithEncounter();
	}
	
	@Override
	public String getDescription() {
		return "A dark shroud hangs over this place, clouding your mind and your vision. Within, you hear something that sounds "
				+ "like the wind gasping for breath.";
	}

	@Override
	public String getCompletedDescription() {
		return "A dark shroud hangs over this place, though the evil has been vanquished. Your hearbeat quickens instinctually, "
				+ "though you know you're not in any danger.";
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
