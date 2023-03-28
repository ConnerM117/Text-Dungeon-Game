package rooms;

import com.textdungeon.game.GameScreen;

import floors.Floor;
import items.Item;
import items.armor.Armor;
import items.gear.Gear;
import items.weapons.Weapon;
import mobs.*;

public class Armory extends Room {
	
	public Armory(int roomNumber, Floor floor) {
		super(roomNumber, floor);
		name = "Armory";
	}
	
	@Override
	public String getDescription() {
		return "The walls are lined with racks of weapons and shields, and armor stands display sets of old, rusted "
				+ "armor. Perhaps there is something useful here?";
	}

	@Override
	public String getCompletedDescription() {
		return "The walls are lined with racks of weapons and shields, and armor stands display sets of old, rusted "
				+ "armor. What remains is decrepit and useless.";
	}

	@Override
	public void initRoomActions(Player player) {

		RoomAction lookArmor = new RoomAction("Look for Armor") {
			@Override
			public String resolveAction(Player player) {
				StringBuilder str = new StringBuilder();
				str.append("You search the armory for armor that may have survived the test of time.\n");
				Armor armor = Armor.getRandArmorWeighted(floor.getFloorNumber());
				str.append("You find a set of " + armor.getName() + " in serviceable condition. ");
				str.append(player.addToInventory(armor));
				
				if (GameScreen.generateRandom(1, 2) == 1) {
					int rand = GameScreen.generateRandom(1, 2);
					Item item = null;
					if (rand == 1) {
						item = Weapon.getRandWeaponWeighted(floor.getFloorNumber());
					} else {
						item = Gear.getRandGear();
					}
					str.append("\nYou also find a " + item.getName() + ". ");
					str.append(player.addToInventory(item));
				}
				
				setCompleted(true);
				return str.toString();
			}
		};
		
		RoomAction lookGear = new RoomAction("Look for Gear") {
			@Override
			public String resolveAction(Player player) {
				StringBuilder str = new StringBuilder();
				str.append("You search the armory for other gear that may have survived the test of time.\n");
				Gear gear = Gear.getRandGear();
				str.append("You find a " + gear.getName() + " in serviceable condition. ");
				str.append(player.addToInventory(gear));
				
				if (GameScreen.generateRandom(1, 2) == 1) {
					int rand = GameScreen.generateRandom(1, 2);
					Item item = null;
					if (rand == 1) {
						item = Weapon.getRandWeaponWeighted(floor.getFloorNumber());
						str.append("\nYou also find a " + item.getName() + ". ");
					} else {
						item = Armor.getRandArmor();
						str.append("\nYou also find a set of " + item.getName() + ". ");
					}
					
					str.append(player.addToInventory(item));
				}
				
				setCompleted(true);
				return str.toString();
			}
		};
		
		RoomAction lookWeapon = new RoomAction("Look for a Weapon") {
			@Override
			public String resolveAction(Player player) {
				StringBuilder str = new StringBuilder();
				str.append("You search the armory for a weapon that may have survived the test of time.\n");
				Weapon weapon = Weapon.getRandWeaponWeighted(floor.getFloorNumber());
				str.append("You find a " + weapon.getName() + " in serviceable condition. ");
				str.append(player.addToInventory(weapon));
				
				if (GameScreen.generateRandom(1, 2) == 1) {
					int rand = GameScreen.generateRandom(1, 2);
					Item item = null;
					if (rand == 1) {
						item = Gear.getRandGear();
						str.append("\nYou also find a " + item.getName() + ". ");
					} else {
						item = Armor.getRandArmor();
						str.append("\nYou also find a set of " + item.getName() + ". ");
					}
					
					str.append(player.addToInventory(item));
				}
				
				setCompleted(true);
				return str.toString();
			}
		};
		
		roomActions.add(lookArmor);
		roomActions.add(lookGear);
		roomActions.add(lookWeapon);
		
	}

}
