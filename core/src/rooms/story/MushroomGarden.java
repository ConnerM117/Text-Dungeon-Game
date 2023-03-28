package rooms.story;

import combat.CombatEncounter;
import combat.MushroomEncounter;
import floors.Floor;
import items.consumables.BlueMushroom;
import items.consumables.SketchyMushroom;
import mobs.Player;
import rooms.ScriptedCombatRoom;

public class MushroomGarden extends ScriptedCombatRoom {
	
	public MushroomGarden(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Mushroom Garden";
		type = Type.STORY;
		combatChance = NO_COMBAT_CHANCE;
		droppedItems.add(new BlueMushroom());
		for (int i = 0; i < 3; i ++) {
			droppedItems.add(new SketchyMushroom());
		}
	}
	
	@Override
	public CombatEncounter getCombatEncounter() {
		return new MushroomEncounter();
	}
	
	@Override
	public String getDescription() {
		return "Glowing mushrooms dimly light rows of different funguses, mostly mushrooms. A small patch of blue mushrooms "
				+ "lies farther back, under the gaze of an enormous mushroom that moves as if it is breathing.";
	}

	@Override
	public String getCompletedDescription() {
		return "Glowing mushrooms dimly light rows of different funguses, mostly mushrooms. The remains of an enormous hostile mushroom "
				+ "lie scattered.";
	}

	@Override
	public void initRoomActions(Player player) {
		
		if (!isCompleted()) {
			RoomAction attack = new RoomAction("Harvest Mushrooms") {
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
