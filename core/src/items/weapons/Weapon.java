package items.weapons;

import java.util.Objects;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.runes.Rune;
import mobs.Player;

/**
 * Weapons have points equal to Tier + 3. 1 point buys: 2.5 damage (rolled
 * average), 5% in a stat, 1 piercing damage, 1 magic damage, 2 Wound damage, 20
 * Crit Damage 
 * Axes: Wound Rate/Damage, Crit Rate/Damage. 
 * Bludgeons: crit rate/damage, Accuracy. 
 * Piercing: piercing damage and Accuracy, Dodge. 
 * Swords: Dodge/Accuracy, Crit/Wound 
 * Spears: Any
 *
 */
public abstract class Weapon extends Item {

	protected boolean isLight; // when you dual-wield, one of the weapons must be Light
	protected boolean isImproved;
	protected int handsRequired; // will always be 1 or 2
	protected int minDamage;
	protected int maxDamage;
	protected int piercingDamage; // ignores armor
	protected int magicDamageMod;
	protected int critRateMod;
	protected int critDamageMod;
	protected int dodgeMod;
	protected int accuracyMod;
	protected int woundRateMod;
	protected int woundDamageMod;
	protected Rune rune;

	public Weapon() {
		name = "";
		count = 1;
		cost = 0;
		isEquippable = true;
		isEquipped = false;
		isImproved = false;
		type = Type.WEAPON;
		isLight = false;
		handsRequired = 0;
		minDamage = 0;
		maxDamage = 0;
		piercingDamage = 0;
		magicDamageMod = 0;
		critRateMod = 0;
		critDamageMod = 0;
		dodgeMod = 0;
		accuracyMod = 0;
		woundRateMod = 0;
		woundDamageMod = 0;
	}

	@Override
	public void equipEffects(Player player) {
		if (Objects.nonNull(rune))
			rune.equipEffects(player);
		return;
	}

	@Override
	public void unequipEffects(Player player) {
		if (Objects.nonNull(rune))
			rune.unequipEffects(player);
		return;
	}
	
	@Override
	public String getName() {
		if (rune != null)
			return name + " (Rune)";
		return name;
	}
	
	@Override
	public int getCost() {
		if (rune == null) { //if there is no rune, return the cost of the base weapon
			return cost;
		} else { //if there is a rune, return the cost of the weapon + the cost of the rune
			return cost + rune.getCost();
		}
	}
	
	public int getDamage() {
		return GameScreen.generateRandom(minDamage, maxDamage);
	}

	public int getMinDamage() {
		return minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public int getPiercingDamage() {
		return piercingDamage;
	}

	public int getHandsRequired() {
		return handsRequired;
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

	public int getDodgeMod() {
		return dodgeMod;
	}

	public int getAccuracyMod() {
		return accuracyMod;
	}

	public int getWoundRateMod() {
		return woundRateMod;
	}

	public int getWoundDamageMod() {
		return woundDamageMod;
	}

	public boolean isLight() {
		return isLight;
	}
	
	public Rune getRune() {
		return rune;
	}
	
	public boolean isImproved() {
		return isImproved;
	}
	
	public void setImproved(boolean isImproved) {
		this.isImproved = isImproved;
	}
	
	public void modMinDamage(int mod) {
		minDamage += mod;
	}
	
	public void modMaxDamage(int mod) {
		maxDamage += mod;
	}
	
	public void modPiercingDamage(int mod) {
		piercingDamage += mod;
	}
	
	public void modMagicDamage(int mod) {
		magicDamageMod += mod;
	}
	
	public void modCritRate(int mod) {
		critRateMod += mod;
	}
	
	public void modCritDamage(int mod) {
		critDamageMod += mod;
	}
	
	public void modDodgeMod(int mod) {
		dodgeMod += mod;
	}
	
	public void modAccuracyMod(int mod) {
		accuracyMod += mod;
	}
	
	public void modWoundRate(int mod) {
		woundRateMod += mod;
	}
	
	public void modWoundDamage(int mod) {
		woundDamageMod += mod;
	}
	
	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}
	
	public String setRune(Rune rune) {
		String str = "";
		if (Objects.nonNull(this.rune)) {
			this.rune.revertChanges(this);
			str += "The " + this.rune.getName() + " fades.\n";
		}
		this.rune = rune;
		str += "The " + rune.getName() + " was transferred to the " + name + ".";
		return str;
	}

	public String getStatistics() {
		StringBuilder str = new StringBuilder();
		str.append(description);
		if (handsRequired == 1)
			str.append("\nThis weapon requires one hand to wield.");
		else if (handsRequired == 2)
			str.append("\nThis weapon requires two hands to wield.");

		str.append("\n" + minDamage + "-" + maxDamage + " damage");
		if (piercingDamage > 0)
			str.append(", " + piercingDamage + " Piercing Damage");

		if (critRateMod > 0 || critDamageMod > 0)
			str.append("\n+" + critRateMod + " Crit Rate, +" + critDamageMod + " Crit Damage.");
		if (accuracyMod > 0 || dodgeMod > 0) {
			str.append("\n");
			if (accuracyMod > 0)
				str.append("+" + accuracyMod + " Accuracy ");
			if (dodgeMod > 0)
				str.append("+" + dodgeMod + " Dodge");
		}
		if (woundRateMod > 0 || woundDamageMod > 0)
			str.append("\n+" + woundRateMod + " Wound Rate, +" + woundDamageMod + " Wound Damage.");
		str.append("\nPrice: " + cost);
		
		if (rune != null) {
			str.append("\n\nRune: " + rune.getName() + "\n" + rune.getDescription());
		}
		
		return str.toString();
	}

	public static Weapon getRandTier1Weapon() {
		int rand = GameScreen.generateRandom(1, 6);
		switch (rand) {
		case 1:
			return new ArmingSword();
		case 2:
			return new Dagger();
		case 3:
			return new Handaxe();
		case 4:
			return new Mace();
		case 5:
			return new Shortsword();
		case 6:
			return new Spear();
		default:
			return new ArmingSword();
		}
	}

	public static Weapon getRandTier2Weapon() {
		int rand = GameScreen.generateRandom(1, 8);
		switch (rand) {
		case 1:
			return new Battleaxe();
		case 2:
			return new Flail();
		case 3:
			return new Longsword();
		case 4:
			return new Pike();
		case 5:
			return new Rapier();
		case 6:
			return new Warhammer();
		case 7:
			return new Morningstar();
		case 8:
			return new ExecutionerBlade();
		default:
			return new Battleaxe();
		}
	}

	public static Weapon getRandTier3Weapon() {
		int rand = GameScreen.generateRandom(1, 5);
		switch (rand) {
		case 1:
			return new Estoc();
		case 2:
			return new Greataxe();
		case 3:
			return new Greatsword();
		case 4:
			return new Halberd();
		case 5:
			return new Maul();
		default:
			return new Estoc();
		}
	}

	public static Weapon getRandWeapon() {
		int rand = GameScreen.generateRandom(1, 19);
		switch (rand) {
		case 1:
			return new ArmingSword();
		case 2:
			return new Battleaxe();
		case 3:
			return new Dagger();
		case 4:
			return new Estoc();
		case 5:
			return new Flail();
		case 6:
			return new Greataxe();
		case 7:
			return new Greatsword();
		case 8:
			return new Halberd();
		case 9:
			return new Handaxe();
		case 10:
			return new Longsword();
		case 11:
			return new Mace();
		case 12:
			return new Maul();
		case 13:
			return new Pike();
		case 14:
			return new Rapier();
		case 15:
			return new Shortsword();
		case 16:
			return new Spear();
		case 17:
			return new Warhammer();
		case 18:
			return new Morningstar();
		case 19:
			return new ExecutionerBlade();
		default:
			return new ArmingSword();
		}
	}

	/**
	 * Uses the static FloorCounter to weight the results of the random draw. Tier 2
	 * and 3 weapons are much rarer on earlier floors.
	 * 
	 * @return a random weapon.
	 */
	public static Weapon getRandWeaponWeighted(int floor) {
		int rand = GameScreen.generateRandom(1, 100);
		if (rand <= floor * floor)
			return getRandTier3Weapon();
		else if (rand <= floor * 11)
			return getRandTier2Weapon();
		else
			return getRandTier1Weapon();
	}
	
	/**
	 * Obtains a random weighted weapon and weighted rune, applies the rune to the 
	 * weapon and returns it.
	 * @return a random weapon with a random rune already equipped
	 */
	public static Weapon getRandWeaponWithRune(int floor) {
		Weapon weapon = getRandWeaponWeighted(floor);
		Rune rune = Rune.getRandRuneWeighted(floor);
		weapon.setRune(rune);
		return weapon;
	}
	
	/**
	 * Obtains a random weighted weapon and weighted greater rune, applies the rune to the 
	 * weapon and returns it.
	 * @return a random weapon with a random greater rune already equipped
	 */
	public static Weapon getRandWeaponWithGreaterRune(int floor) {
		Weapon weapon = getRandWeaponWeighted(floor);
		Rune rune = Rune.getRandGreaterRune();
		weapon.setRune(rune);
		return weapon;
	}

}
