package items;

import com.textdungeon.game.GameScreen;

import items.armor.*;
import items.consumables.*;
import items.gear.*;
import items.runes.Rune;
import items.weapons.*;
import mobs.Player;

public abstract class Item {
	
	protected String name;
	protected String description;
	protected int count;
	protected int cost;
	protected boolean isEquippable;
	protected boolean isEquipped;

	protected int atlasIndex;
	
	public enum Type {
		ARMOR, CONSUMABLE, GEAR, WEAPON, COINS, KEY, RUNE
	}
	protected Type type;
	
	public Item() {
		name = "";
		count = 1;
		cost = 0;
		isEquippable = false;
		isEquipped = false;
	}
	
	public abstract String getStatistics();
	
	/**
	 * @param player the player's avatar, on which the item can perform its usage
	 * @return a string literal describing the action taken
	 */
	public String useItem(Player player) { return ""; } //only implemented by Consumable items
	
	public void equipEffects(Player player) { return; } //for equippable items that have special properties, access the player and make changes
	
	public void unequipEffects(Player player) { return; } //unequip changes made when equipped
	
	public String getDescription() {
		return description;
	}
	
	public void incrementCount() {
		count++;
	}
	
	public void decrementCount() {
		count--;
	}
	
	public void setEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}
	
	public int getAtlasIndex() {
		return atlasIndex;
	}

	public String getName() {
		return name;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Type getType() {	
		return type;
	}
	
	public boolean isEquippable() {
		return isEquippable;
	}
	
	public boolean isEquipped() {
		return isEquipped;
	}
	
	public static Item getWeightedArmorWeaponGear(int floor) {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1) {
			return Armor.getRandArmorWeighted(floor);
		} else if (rand == 2) {
			return Weapon.getRandWeaponWeighted(floor);
		} else {
			return Gear.getRandGear();
		}
	}
	
	public static Item getWeightedArmorOrWeapon(int floor) {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1) {
			return Armor.getRandArmorWeighted(floor);
		} else {
			return Weapon.getRandWeaponWeighted(floor);
		}
	}
	
	/**
	 * Returns a random item of any kind, weighting the draw on item types
	 * that can be weighted. A weapon has a 1/5 chance of having a weighted rune.
	 * @return a random item
	 */
	public static Item getRandItemWeighted(int floor) {
		int rand = GameScreen.generateRandom(1, 6);		
		switch (rand) {
		case 1: return Armor.getRandArmorWeighted(floor);
		case 2: return Consumable.getRandConsumable();
		case 3: return Rune.getRandRuneWeighted(floor);
		case 4: return Gear.getRandGear();
		case 5: 
			rand = GameScreen.generateRandom(1, 5);
			if (rand == 1)
				return Weapon.getRandWeaponWithRune(floor);
			return Weapon.getRandWeaponWeighted(floor);
		default: return Consumable.getRandConsumable();
		}
	}
	
	public static Item getRandItem() {
		int rand = GameScreen.generateRandom(1, 5);
		switch (rand) {
		case 1: return Armor.getRandArmor();
		case 2: return Consumable.getRandConsumable();
		case 3: return Rune.getRandRune();
		case 4: return Gear.getRandGear();
		case 5: return Weapon.getRandWeapon();
		default: return Consumable.getRandConsumable();
		}
	}
	
	public static Item getRandSpecialItem() {
		int rand = GameScreen.generateRandom(1, 9);
		switch (rand) {
		case 1: return getGoblinItem(); 
		case 2: return getSkeletonItem();
		case 3: return getTrollItem();
		case 4: return getForestItem();
		case 5: return getSwampItem();
		case 6: return getDragonItem();
		case 7: return getDemonItem();
		case 8: return getLichItem();
		case 9: return getEldritchItem();
		default: return getGoblinItem();
		}
	}

	public static Item getEldritchItem() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new EldritchEye();
		else 
			return new EldritchHorn();
	}

	public static Item getLichItem() {
		int rand = GameScreen.generateRandom(1, 5);
		if (rand <= 2)
			return new NecromancyTalisman();
		else if (rand == 3)
			return new AncientSword();
		else if (rand == 4)
			return new AncientAxe();
		else
			return new AncientShield();
	}

	public static Item getDemonItem() {
		int rand = GameScreen.generateRandom(1, 4);
		if (rand == 1)
			return new DemonAshes();
		else if (rand == 2)
			return new DemonHorn();
		else if (rand == 3)
			return new DemonSword();
		else
			return new DemonArmor();
	}

	private static Item getDragonItem() {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1)
			return new DragonhideArmor();
		else if (rand == 2)
			return new WyvernVenom();
		else 
			return new MakeshiftTrap();
	}

	public static Item getSwampItem() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new LizardmanAntidote();
		else 
			return new LizardmanShield();
	}

	public static Item getForestItem() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new BearCloak();
		else 
			return new GiantSnakeFang();
	}

	public static Item getTrollItem() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new OgreClub();
		else 
			return new TrollBrew();
	}

	public static Item getSkeletonItem() {
		int rand = GameScreen.generateRandom(1, 5);
		if (rand <= 2)
			return new RustySword();
		else if (rand == 3)
			return new AncientSword();
		else if (rand == 4)
			return new AncientAxe();
		else
			return new AncientShield();
	}

	public static Item getGoblinItem() {
		int rand = GameScreen.generateRandom(1, 3);
		if (rand == 1)
			return new GoblinKnife();
		else if (rand == 2)
			return new GoblinTalisman();
		else
			return new GoblinMoonshine();
	}

	public static Item getFloorSpecialItem(int floorNumber) {
		switch (floorNumber) {
		case 1: return Consumable.getRandConsumable();
		case 2: return getGoblinItem();
		case 3: return getSkeletonItem();
		case 4: return getTrollItem();
		case 5: return getForestItem();
		case 6: return getSwampItem();
		case 7: return getLichItem();
		case 8: return getDragonItem();
		case 9: return getDemonItem();
		case 10: return getEldritchItem();
		default: return Consumable.getRandConsumable();
		}
	}
	
}
