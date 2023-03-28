package items.gear;

import com.textdungeon.game.GameScreen;

import items.Item;

public abstract class Gear extends Item {
	
	protected int handsRequired; //can only have 2 hands worth of gear equipped at a time, including weapons
	protected int armorMod;
	protected int damageMod;
	protected int magicDamageMod;
	protected int critRateMod;
	protected int critDamageMod;
	protected int agilityMod;
	protected int mindMod;
	protected int accuracyMod;
	protected int woundRateMod;
	protected int woundDamageMod;
	protected int toughnessMod;
	protected int staminaMod;
	boolean isEquipped;
	
	public enum WornOn { 
		HANDS, HEAD, MISC
	}
	protected WornOn worn;
	
	public Gear() {
		name = "";
		description = "";
		count = 1;
		cost = 0;
		isEquippable = true;
		handsRequired = 0;
		armorMod = 0;
		damageMod = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		agilityMod = 0;
		mindMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
		toughnessMod = 0;
		staminaMod = 0;
		isEquipped = false;
		type = Type.GEAR;
		worn = WornOn.MISC;
		
	}

	public int getHandsRequired() {
		return handsRequired;
	}
	
	public WornOn getWornType() {
		return worn;
	}
	
	public int getArmorMod() {
		return armorMod;
	}
	
	public int getDamageMod() {
		return damageMod;
	}
	
	public int getMagicDamageMod() {
		return magicDamageMod;
	}

	public int getCritRateMod() {
		return critRateMod;
	}

	public int getCritDamageMod() {
		return critDamageMod;
	}

	public int getAgilityMod() {
		return agilityMod;
	}

	public int getAccuracyMod() {
		return accuracyMod;
	}
	
	public int getWoundRateMod() {
		return woundRateMod;
	}
	
	public int getWoundDamageMod()
	{	
		return woundDamageMod;
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
	
	public String getStatistics() {
		StringBuilder str = new StringBuilder();
		switch(worn) {
		case HEAD:
			str.append(description + "\nWorn on the head.");
			break;
		case HANDS:
			str.append(description + "\nHeld in " + handsRequired + " hand(s).");
			break;
		case MISC:
			str.append(description + "\nMiscellaneous Gear");
			break;
		}
		
		if (armorMod > 0)
			str.append("\nArmor Mod: +" + armorMod);
		if (damageMod > 0)
			str.append("\nDamage Mod: +" + damageMod);
		if (magicDamageMod > 0)
			str.append("\nMagic Damage Mod: +" + magicDamageMod);
		if (agilityMod > 0)
			str.append("\nAgility Mod: +" + agilityMod);
		else if (agilityMod < 0)
			str.append("\nAgility Mod: " + agilityMod);
		if (toughnessMod > 0)
			str.append("\nToughness Mod: +" + toughnessMod);
		if (mindMod > 0)
			str.append("\nMind Mod: +" + mindMod);
		if (accuracyMod > 0)
			str.append("\nAccuracy Mod: +" + accuracyMod);
		if (critRateMod > 0)
			str.append("\nCrit Rate Mod: +" + critRateMod);
		if (critDamageMod > 0)
			str.append("\nCrit Damage Mod: +" + critDamageMod);
		if (woundRateMod > 0)
			str.append("\nWound Rate Mod: +" + woundRateMod);
		if (critDamageMod > 0)
			str.append("\nWound Damage Mod: +" + woundDamageMod);
		if (staminaMod > 0)
			str.append("\nStamina Mod: +" + staminaMod);
		
		str.append("\nPrice: " + cost);
		
		return str.toString();
	}
	
	public static Gear getRandGear() {
		int rand = GameScreen.generateRandom(1, 7);
		switch (rand) {
		case 1: return new Buckler();
		case 2: return new Cloak();
		case 3: return new GreatHelm();
		case 4: return new LightHelm();
		case 5: return new Shield();
		case 6: return new TowerShield();
		case 7: return getRandStaff();
		}
		return new LightHelm();
	}

	public static Gear getRandStaff() {
		int rand = GameScreen.generateRandom(1, 10);
		if (rand < 4) {
			return new LesserStaff();
		} else if (rand < 7) {
			return new Staff();
		} else if (rand < 9) {
			return new GreaterStaff();
		} else {
			return new StaffMagic();
		}
	}
	
}
