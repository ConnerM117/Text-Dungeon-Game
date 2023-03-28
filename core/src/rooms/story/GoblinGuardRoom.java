package rooms.story;

import combat.CombatEncounter;
import combat.GoblinGuardEncounter;
import floors.Floor;
import items.Item;
import items.KeyItem;
import mobs.Player;
import rooms.ScriptedCombatRoom;

public class GoblinGuardRoom extends ScriptedCombatRoom {
	
	public GoblinGuardRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Guard Room";
		type = Type.STORY;
		combatChance = NO_COMBAT_CHANCE;
		droppedItems.add(new KeyItem(GoblinPrison.PRISON_KEY));
		droppedItems.add(Item.getGoblinItem());
		droppedItems.add(Item.getGoblinItem());
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new GoblinGuardEncounter();
	}
	
	@Override
	public String getDescription() {
		return "Goblins sit on crude furniture, drinking and belching with their weapons at their sides. One of them has a large "
				+ "key hanging from her belt.";
	}

	@Override
	public String getCompletedDescription() {
		return "Crude goblin furniture lines this room, as well as a rack of weapons and a cask of something foul-smelling.";
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
