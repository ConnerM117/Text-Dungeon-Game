package rooms;

import floors.Floor;
import items.weapons.Weapon;
import mobs.Player;

public class RelicWeaponRoom extends Room {

	private Weapon weapon;
	
	public RelicWeaponRoom(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Lost Relic";
		weapon = Weapon.getRandWeaponWithGreaterRune(floor.getFloorNumber());
	}

	@Override
	public String getDescription() {
		return "A glowing weapon floats here, amidst whispering fog that obscures the edges of the room. The weapon appears to  be a " 
				+ weapon.getName() + " with a " + weapon.getRune().getName() + ".";
	}

	@Override
	public String getCompletedDescription() {
		return "Whispering fog swirls around the room, obscuring its dimensions entirely.";
	}

	@Override
	public void initRoomActions(Player player) {
		RoomAction takeLoot = new RoomAction("Take Weapon") {
			@Override
			public String resolveAction(Player player) {
				setCompleted(true);
				player.addToInventory(weapon);
				return "You take up the weapon and its glow slowly fades, but you can feel the power contained within it.";
			}
		};
		
		roomActions.add(takeLoot);
	}

}
