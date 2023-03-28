package items.armor;

import com.textdungeon.game.GameScreen;

import items.Item;

/**
 * Armor provides protection from damage, represented by int armor.
 * This variable is a range (minArmor and maxArmor), incoming damage is reduced by a random number between the two.
 * Toughness/20 is added to your armor before reduction.
 * The more powerful the armor, the greater its dodge penalty, but it is always better to have
 * more protective armor.
 * Dodge, Toughness, and Mind mods otherwise shouldn't be touched. They're placeholders until
 * I can implement Glyphs and Runes.
 */
public abstract class Armor extends Item {
	
	protected int minArmor;
	protected int maxArmor;
	protected int agilityMod;
	protected int toughnessMod;
	protected int mindMod;
	protected int staminaMod;
	boolean isEquipped;
	protected boolean isImproved;
	
	public Armor() {
		name = "";
		description = "";
		count = 1;
		isEquippable = true;
		minArmor = 0;
		maxArmor = 0;
		agilityMod = 0;
		staminaMod = 0;
		isEquipped = false;
		isImproved = false;
		type = Type.ARMOR;
	}

	public int getArmor() {
		return GameScreen.generateRandom(minArmor, maxArmor);
	}
	
	public int getMinArmor() {
		return minArmor;
	}
	
	public int getMaxArmor() {
		return maxArmor;
	}

	public int getAgilityMod() {
		return agilityMod;
	}
	
	public int getToughnessMod() {
		return toughnessMod;
	}
	
	public int getMindMod() {
		return mindMod;
	}
	
	public int getStaminaMod() {
		return staminaMod;
	}
	
	public boolean isImproved() {
		return isImproved;
	}
	
	public void setImproved(boolean isImproved) {
		this.isImproved = isImproved;
	}
	
	public void modMaxArmor(int mod) {
		maxArmor += mod;
	}
	
	public String getStatistics() {	
		return getDescription() + "\nArmor: " + minArmor + "-" + maxArmor + "; Agility Mod: " + agilityMod + "\nPrice: " + cost;
	}
	
	public static Armor getRandArmor() {
		int rand = GameScreen.generateRandom(1, 6);
		switch (rand) {
		case 1: return new ChainMail();
		case 2: return new LeatherArmor();
		case 3: return new PaddedArmor();
		case 4: return new PlateArmor();
		case 5: return new SplintArmor();
		case 6: return new EnchantedRobes();
		default: return new LeatherArmor();
		}
	}
	
	/**
	 * Generates two random armors, and subtracts the current floor from the maxArmor of each.
	 * Take absolute value to make sure both are positive. The armor with the lower result (closest to 0 is chosen).
	 * This weights the armor to have a value closer to the floor you're on.
	 * @return the armor with the result closest to 0.
	 */
	public static Armor getRandArmorWeighted(int floor) {
		Armor armor1 = getRandArmor();
		Armor armor2 = getRandArmor();
		if (Math.abs(armor1.getMaxArmor() - floor) < Math.abs(armor2.getMaxArmor() - floor)) {
			return armor1;
		} else {
			return armor2;
		}
	}

}
