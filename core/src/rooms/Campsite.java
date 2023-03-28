package rooms;

import floors.Floor;
import items.consumables.Ration;
import mobs.Player;

public class Campsite extends Room {
	
	public Campsite(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Campsite";
		combatChance = NO_COMBAT_CHANCE;
	}
	
	@Override
	public String getDescription() {
		return "This place isn't so dreary. The air is clearer, the ground... dirty, but not so bad compared to the rest of the dungeon. "
				+ "There's even something you can use as firewood.";
	}

	@Override
	public String getCompletedDescription() {
		return "You... shouldn't have been able to complete a campsite. How did you do this?";
	}

	@Override
	public void initRoomActions(Player player) {
		
		RoomAction makeCamp = new RoomAction("Make Camp") {
			@Override
			public String resolveAction(Player player) {
				Ration ration = new Ration();
				if (player.getInventory().containsKey(ration.getName())) {
					String str = player.getName() + " takes some time to eat and rest.";
					str += "\n" + player.takeFatigue(ration.getFatigueReduction() * 2);
					str += "\n" + player.healDamage((player.getMaxHitPoints() / ration.getHealValue()) * 2);
					str += "\n" + player.healStamina(player.getBaseStamina());
					player.removeFromInventory(ration);
					return str;
				} else {
					return "You need Rations to Make Camp!";
				}
			}
		};
		
		roomActions.add(makeCamp);
	}

}
