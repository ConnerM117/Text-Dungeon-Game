package mobs;

import java.util.*;

import com.textdungeon.game.CharacterSelectScreen.AdventurerChoice;
import com.textdungeon.game.CharacterSelectScreen.ArchetypeChoice;
import com.textdungeon.game.CharacterSelectScreen.KinChoice;
import com.textdungeon.game.CharacterSelectScreen;
import com.textdungeon.game.GameScreen;

import adventurers.*;
import adventurers.archetypes.Archetype;
import adventurers.archetypes.Assassin;
import adventurers.archetypes.Berserker;
import adventurers.archetypes.Burglar;
import adventurers.archetypes.Illusionist;
import adventurers.archetypes.Knight;
import adventurers.archetypes.ManAtArms;
import adventurers.archetypes.Occultist;
import adventurers.archetypes.Pyromancer;
import adventurers.archetypes.Troubadour;
import adventurers.choices.Aim;
import adventurers.choices.Choice;
import adventurers.choices.Defend;
import adventurers.choices.mage.MageAttack;
import adventurers.choices.rogue.RogueAttack;
import adventurers.choices.warrior.WarriorAttack;
import adventurers.perks.general.Perk;
import adventurers.perks.kin.Adaptable;
import adventurers.perks.kin.Brutal;
import adventurers.perks.kin.DwarfNotice;
import adventurers.perks.kin.DwarfResilient;
import adventurers.perks.kin.GracefulFighter;
import adventurers.perks.kin.JackOfAllTrades;
import adventurers.perks.kin.Nimble;
import adventurers.perks.kin.Powerhouse;
import core.*;
import items.Item;
import items.armor.*;
import items.consumables.HealingPotion;
import items.consumables.Ration;
import items.consumables.StolenGoods;
import items.gear.Gear;
import items.weapons.*;

//TODO display Statistics doesn't include item bonuses on Armor. In fact, I don't think any show item bonuses.

public class Player extends Mob {

	private static final int EXTRA_HAND_DAMAGE_BONUS = 1;
	private static final int HAND_ITEM_SLOTS = 2;
	private static final int BEGINNING_INVENTORY_MAX = 15;
	public final int REQUIRED_LEVEL_XP = 20;
	public static final int FATIGUE_MAX = 20;
	public final String STOLEN_GOODS = new StolenGoods().getName();
	public static final int CURSE_MULTIPLIER = 2;

	private final String DETAILS_SPACING = "\n   ";

	private int handSlotsFree;

	private Map<String, Item> inventory;
	private int maxInventory;
	private Set<String> keyRing;

	private List<Choice> combatChoices;
	private List<Perk> perkChoices;
	private List<Perk> chosenPerks;

	private List<Condition> magicDamageConditions;

	private Weapon equippedWeapon;
	private Weapon secondaryWeapon; // only not null if dual-wielding
	private Armor equippedArmor;
	private Gear equippedMiscGear;
	private Gear equippedHeadGear;
	private Gear equippedHandGear;
	private boolean isDualWielding;

	private int level;
	private int coins;
	private int minMagicDamage; // damage only dealt by Mages with spells
	private int maxMagicDamage;
	private int fatigue;
	private int curse;

	/**
	 * Only increased when player attack misses, and resets to 0 when attack hits,
	 * to prevent strings of attacks that all miss
	 */
	private int accuracyBuff;

	private boolean weaponIsPoisoned;
	private int weaponPoisonRounds;
	private boolean weaponIsBurning;
	private int weaponBurningRounds;
	private boolean hasSecondAction;
	private boolean weaponPoisonedPermanent;
	private boolean weaponBurningPermanent;

	private String kin;
	private String adventurerType;
	private Adventurer adventurer;
	private Archetype archetype;

	private CharacterSelectScreen.KinChoice kinChoice;
	private CharacterSelectScreen.AdventurerChoice adventurerChoice;
	private CharacterSelectScreen.ArchetypeChoice archetypeChoice;

	private boolean isHoly;
	private boolean isSpellcaster;
	private boolean isSurvivor;
	private boolean survivorUsed;
	private boolean isSwindler;

	// kin perks
	private boolean isResilient;
	private final int DWARF_ARMOR_BUFF = 1;
	private boolean hasEyeForDetail;
	private final int DWARF_NOTICE_BUFF = 20;
	private boolean isGracefulFighter;
	private final int ELF_GRACE_BONUS = 10;
	private boolean isNimble;
	private final int ELF_NIMBLE_BONUS = 20;
	private boolean isPowerhouse;
	private final int ORC_STR_BUFF = 20;

	// Mage perks
	private boolean hasUltimatePhantasm;
	private boolean hasDarkBargain;
	private boolean hasFeedTheFlames;
	private boolean hasFlameWard;
	private boolean hasIncinerate;
	private boolean hasSacrifice;

	// Rogue perks
	private boolean hasAmbush;
	private boolean hasBravado;
	private boolean hasDeathblow;
	private boolean isEvasive;
	private boolean isExpertThief;
	private boolean hasFootwork;
	private boolean hasItchyPalm;
	private final int ITCHYPALMCOINS = 1;
	private boolean hasPhantom;
	private boolean isScout;
	private boolean hasSkeletonKey;
	private boolean isTrapExpert;

	// Warrior perks
	private boolean hasBladeWall;
	private boolean hasBloodlust;
	private boolean hasCleave;
	private boolean hasFeint;
	private final int FEINTACCURACYBUFF = 20;
	private final int FEINTCRITRATEBUFF = 10;
	private boolean hasFollowUp;
	private final int FOLLOWUPCRITRATEBUFF = 10;
	private boolean hasGuidedSmite;
	private boolean hasIronhide;
	private boolean hasKnightDuel;
	private Mob knightDuelTarget; // points to the object of the Knight's Duel
	private final int KNIGHTDUELDAMAGEBUFF = 1;
	private final int KNIGHTDUELARMORBUFF = 2;
	private boolean hasToughRage;
	private boolean hasWoundedFury;
	private int woundedFuryCritBonus;

	// constructor
	public Player() {
		inventory = new HashMap<>();
		maxInventory = BEGINNING_INVENTORY_MAX;
		level = 1;
		keyRing = new HashSet<>();

		perkChoices = new ArrayList<>();
		combatChoices = new ArrayList<>();
		chosenPerks = new ArrayList<>();

		handSlotsFree = HAND_ITEM_SLOTS;

		equippedWeapon = null;
		secondaryWeapon = null;
		equippedArmor = null;
		equippedMiscGear = null;
		equippedHeadGear = null;
		equippedHandGear = null;
		isDualWielding = false;

		XP = 0;
		maxHitPoints = 10;
		currentHitPoints = 10;
		minArmor = 0;
		maxArmor = 0;
		baseAgility = 15;
		baseToughness = 15;
		baseMind = 15;
		baseStrength = 15;
		baseAccuracy = 80;
		critRate = 5;
		critDamage = 25; // expressed as a percentage of total damage done
		woundRate = 5;
		woundDamage = 1;
		minDamage = 1;
		maxDamage = 2; // damage without any weapons
		piercingDamage = 0;
		baseStamina = 4;
		currentStamina = 4;
		coins = 0;
		minMagicDamage = 3;
		maxMagicDamage = 5;
		fatigue = 0;

		armorConditions = new ArrayList<>();
		agilityConditions = new ArrayList<>();
		toughnessConditions = new ArrayList<>();
		mindConditions = new ArrayList<>();
		accuracyConditions = new ArrayList<>();
		magicDamageConditions = new ArrayList<>();
		damageConditions = new ArrayList<>();
		critRateConditions = new ArrayList<>();
		critDamageConditions = new ArrayList<>();
		woundRateConditions = new ArrayList<>();
		woundDamageConditions = new ArrayList<>();

		currentHitPoints = getMaxHitPoints();
		currentStamina = getBaseStamina(); // Do this at the end so if they have any HP/Stamina buffs, they begin at
											// full HP/Stamina
	}

	// public methods

	public String displayLevel() {
		return "Level " + getLevel() + ", XP: " + getXP() + "/" + REQUIRED_LEVEL_XP;
	}

	public String displayHitPoints() {
		String str = "";
		if (getCurrentHitPoints() <= getMaxHitPoints() / 4) {
			str += "Hit Points: " + getCurrentHitPoints() + "/" + getMaxHitPoints();
		} else {
			str += "Hit Points: " + getCurrentHitPoints() + "/" + getMaxHitPoints();
		}
		if (getTempHP() > 0) { // if there are tempHP, then display them with HP
			str += " (" + getTempHP() + " Shield)";
		}
		return str;
	}

	public String displayStamina() {
		if (getCurrentStamina() == 0) {
			return "Stamina: " + getCurrentStamina() + "/" + getBaseStamina();
		} else {
			return "Stamina: " + getCurrentStamina() + "/" + getBaseStamina();
		}
	}

	public String displayFatigue() {
		if (fatigue == FATIGUE_MAX) {
			return "Fatigue: " + fatigue + "/" + FATIGUE_MAX;
		} else {
			return "Fatigue: " + fatigue + "/" + FATIGUE_MAX;
		}
	}

	public String displayDamage() {
		String str = "";
		int damage = getBonusDamage();
		for (Condition c : damageConditions) {
			damage += c.getBuff();
		}
		if (equippedWeapon == null) {
			str += "Damage: " + (minDamage + damage) + "-" + (maxDamage + damage);
		} else if (isDualWielding) {
			str += "Damage: " + (equippedWeapon.getMinDamage() + damage) + "-"
					+ (equippedWeapon.getMaxDamage() + damage) + " or " + (secondaryWeapon.getMinDamage() + damage)
					+ "-" + (secondaryWeapon.getMaxDamage() + damage);
		} else {
			str += "Damage: " + (equippedWeapon.getMinDamage() + damage) + "-"
					+ (equippedWeapon.getMaxDamage() + damage);
		}
		if (equippedWeapon != null && equippedWeapon.getPiercingDamage() > 0)
			str += " (" + equippedWeapon.getPiercingDamage() + " Piercing)";
		return str;
	}

	public String displayDamageDetails() {
		StringBuilder str = new StringBuilder();
		String damageMods = "";
		if (equippedWeapon == null) {
			damageMods = "(Base: " + minDamage + "-" + maxDamage;
		} else {
			damageMods = "(Base: " + equippedWeapon.getMinDamage() + "-" + equippedWeapon.getMaxDamage();
		}

		int damage = getBonusDamage();
		if (getCurrentStrength() / 20 > 0)
			damageMods += "; Strength Bonus: +" + getCurrentStrength() / 20;
		if (equippedWeapon.getHandsRequired() == 1 && handSlotsFree == 1) // if they're wielding with one hand and have
																			// a hand free
			damageMods += "; Extra Hand Bonus: +" + EXTRA_HAND_DAMAGE_BONUS + ";"; // deal +1 damage

		for (Condition c : damageConditions) {
			damageMods += " + " + c.getBuff();
			damage += c.getBuff();
		}
		damageMods += ")";
		if (equippedWeapon == null) {
			str.append("Damage: " + (minDamage + damage) + "-" + (maxDamage + damage));
		} else if (isDualWielding) {
			str.append("Damage: " + (equippedWeapon.getMinDamage() + damage) + "-"
					+ (equippedWeapon.getMaxDamage() + damage) + " or " + (secondaryWeapon.getMinDamage() + damage)
					+ "-" + (secondaryWeapon.getMaxDamage() + damage) + DETAILS_SPACING + damageMods);
		} else {
			str.append("Damage: " + (equippedWeapon.getMinDamage() + damage) + "-"
					+ (equippedWeapon.getMaxDamage() + damage) + DETAILS_SPACING + damageMods);
		}

		if (equippedWeapon != null && equippedWeapon.getPiercingDamage() > 0)
			str.append(" (" + equippedWeapon.getPiercingDamage() + " Piercing)");

		return str.toString();
	}

	public String displayArmor() {
		int armor = getArmorItemBonuses();
		for (Condition c : armorConditions) {
			armor += c.getBuff();
		}
		if (equippedArmor == null) {
			return "Armor: " + armor;
		}
		return "Armor: " + (equippedArmor.getMinArmor() + armor) + "-" + (equippedArmor.getMaxArmor() + armor);
	}

	public String displayArmorDetails() {
		String armorMods = "(Base: " + equippedArmor.getMinArmor() + "-" + equippedArmor.getMaxArmor();
		int armor = getArmorItemBonuses();
		if (armor > 0)
			armorMods += "; Gear Bonus: " + armor + ";";

		for (Condition c : armorConditions) {
			armorMods += " + " + c.getBuff();
			armor += c.getBuff();
		}
		return "Armor: " + (equippedArmor.getMinArmor() + armor) + "-" + (equippedArmor.getMaxArmor() + armor)
				+ DETAILS_SPACING + armorMods;
	}

	public String displayAccuracy() {
		return "Accuracy: " + getCurrentAccuracy() + "%";
	}

	public String displayAccuracyDetails() {
		String accuracyMods = "(Base: " + baseAccuracy;
		if (getAccuracyItemBonuses() > 0)
			accuracyMods += "; Gear Bonus: " + getAccuracyItemBonuses() + ";";

		for (Condition c : accuracyConditions) {
			accuracyMods += " + " + c.getBuff();
		}
		accuracyMods += ")";
		return "Accuracy: " + getCurrentAccuracy() + "%" + DETAILS_SPACING + accuracyMods;
	}

	public String displayAgility() {
		return "Agility: " + getCurrentAgility() + "%";
	}

	public String displayAgilityDetails() {
		String agilityMods = "(Base: " + baseAgility;
		if (getAgilityItemBonuses() > 0)
			agilityMods += "; Gear Bonus: " + getAgilityItemBonuses() + ";";

		for (Condition c : agilityConditions) {
			agilityMods += " + " + c.getBuff();
		}
		agilityMods += ")";
		return "Agility: " + getCurrentAgility() + "%" + DETAILS_SPACING + agilityMods;
	}

	public String displayMind() {
		return "Mind: " + getCurrentMind() + "%";
	}

	public String displayMindDetails() {
		String mindMods = "(Base: " + baseMind;
		if (getMindItemBonuses() > 0)
			mindMods += "; Gear Bonus: " + getMindItemBonuses() + ";";

		for (Condition c : mindConditions) {
			mindMods += " + " + c.getBuff();
		}
		mindMods += ")";
		return "Mind: " + getCurrentMind() + "%" + DETAILS_SPACING + mindMods;
	}

	public String displayStrength() {
		return "Strength: " + getCurrentStrength() + "%";
	}

	public String displayStrengthDetails() {
		String strengthMods = "(Base: " + baseStrength;

		for (Condition c : strengthConditions) {
			strengthMods += " + " + c.getBuff();
		}
		strengthMods += ")";
		return "Strength: " + getCurrentStrength() + "%" + DETAILS_SPACING + strengthMods;
	}

	public String displayToughness() {
		return "Toughness: " + getCurrentToughness() + "%";
	}

	public String displayToughnessDetails() {
		String toughnessMods = "(Base: " + baseToughness;
		if (getToughnessItemBonuses() > 0)
			toughnessMods += "; Gear Bonus: " + getToughnessItemBonuses() + ";";

		for (Condition c : toughnessConditions) {
			toughnessMods += " + " + c.getBuff();
		}
		toughnessMods += ")";
		return "Toughness: " + getCurrentToughness() + "%" + DETAILS_SPACING + toughnessMods;
	}

	public String displayMagicDamage() {
		return "Magic Damage: " + (minMagicDamage + getMagicDamageItemBonuses() + getBonusMagicDamage()) + "-"
				+ (maxMagicDamage + getMagicDamageItemBonuses() + getBonusMagicDamage());
	}

	public String displayMagicDamageDetails() {
		String magicDamageMods = "(Base: " + minMagicDamage + "-" + maxMagicDamage;
		if (getBonusMagicDamage() > 0)
			magicDamageMods += "; Mind Bonus: " + getBonusMagicDamage();
		if (getMagicDamageItemBonuses() > 0)
			magicDamageMods += "; Gear Bonus: " + getMagicDamageItemBonuses() + ";";

		for (Condition c : magicDamageConditions) {
			magicDamageMods += " + " + c.getBuff();
		}
		magicDamageMods += ")";
		return displayMagicDamage() + DETAILS_SPACING + magicDamageMods;
	}

	public String displayCritRate() {
		String critRateMods = "(Base: " + critRate;
		if (getCritRateItemBonuses() > 0)
			critRateMods += "; Gear Bonus: " + getCritRateItemBonuses() + ";";

		for (Condition c : critRateConditions) {
			critRateMods += " + " + c.getBuff();
		}
		critRateMods += ")";
		return "Crit Rate: " + getCritRate() + "%" + DETAILS_SPACING + critRateMods;
	}

	public String displayCritDamage() {
		String critDamageMods = "(Base: " + critDamage;
		if (getCritDamageItemBonuses() > 0)
			critDamageMods += "; Gear Bonus: " + getCritDamageItemBonuses() + ";";

		for (Condition c : critDamageConditions) {
			critDamageMods += " + " + c.getBuff();
		}
		critDamageMods += ")";
		return "Crit Damage: " + getCritDamage() + "%" + DETAILS_SPACING + critDamageMods;
	}

	public String displayWoundRate() {
		String woundRateMods = "(Base: " + woundRate;
		if (getWoundRateItemBonuses() > 0)
			woundRateMods += "; Gear Bonus: " + getWoundRateItemBonuses() + ";";

		for (Condition c : woundRateConditions) {
			woundRateMods += " + " + c.getBuff();
		}
		woundRateMods += ")";
		return "Wound Rate: " + getCurrentWoundRate() + "%" + DETAILS_SPACING + woundRateMods;
	}

	public String displayWoundDamage() {
		String woundDamageMods = "(Base: " + woundDamage;
		if (getWoundDamageItemBonuses() > 0)
			woundDamageMods += "; Gear Bonus: " + getWoundDamageItemBonuses() + ";";

		for (Condition c : woundDamageConditions) {
			woundDamageMods += " + " + c.getBuff();
		}
		woundDamageMods += ")";
		return "Wound Damage: " + getCurrentWoundDamage() + DETAILS_SPACING + woundDamageMods;
	}

	public String displayStatistics() {
		return displayLevel() + "\n" + displayHitPoints() + "\n" + displayStamina() + "\n" + displayDamage() + "\n"
				+ displayMagicDamage() + "\n" + displayArmor() + "\n" + displayAccuracy() + "\n" + displayAgility()
				+ "\n" + displayMind() + "\n" + displayStrength() + "\n" + displayToughness() + "\n" + displayFatigue();

	}

	public String displayStatisticsDetails() {
		return displayLevel() + "\n" + displayHitPoints() + "\n" + displayStamina() + "\n" + displayDamageDetails()
				+ "\n" + displayMagicDamageDetails() + "\n" + displayArmorDetails() + "\n" + displayAccuracyDetails()
				+ "\n" + displayAgilityDetails() + "\n" + displayMindDetails() + "\n" + displayStrengthDetails() + "\n"
				+ displayToughnessDetails() + "\n" + displayCritRate() + "\n" + displayCritDamage() + "\n"
				+ displayWoundRate() + "\n" + displayWoundDamage() + "\n" + displayEquippedItems();
	}

	public String displayEquippedItems() {
		StringBuilder str = new StringBuilder("EQUIPPED ITEMS:");
		str.append(DETAILS_SPACING + "Head Gear: ");
		if (equippedHeadGear != null) {
			str.append(equippedHeadGear.getName());
		} else {
			str.append("<None>");
		}

		str.append(DETAILS_SPACING + "Misc Gear: ");
		if (equippedMiscGear != null) {
			str.append(equippedMiscGear.getName());
		} else {
			str.append("<None>");
		}

		str.append(DETAILS_SPACING + "Armor: ");
		if (equippedArmor != null) {
			str.append(equippedArmor.getName());
		} else {
			str.append("<None>");
		}

		str.append(DETAILS_SPACING + "Hands:");
		if (handSlotsFree < 2) {
			if (equippedWeapon != null)
				str.append(DETAILS_SPACING + "   Primary Weapon: " + equippedWeapon.getName());
			if (secondaryWeapon != null)
				str.append(DETAILS_SPACING + "   Secondary Weapon: " + secondaryWeapon.getName());
			if (equippedHandGear != null)
				str.append(DETAILS_SPACING + "   Hand Gear: " + equippedHandGear.getName());
		} else {
			str.append(" <None>");
		}

		return str.toString();
	}

	public void setKin(KinChoice kinChoice) {
		switch (kinChoice) {
		case HUMAN:
			kin = "Human";
			modBaseStrength(5);
			modBaseToughness(5);
			modBaseAgility(5);
			modBaseMind(5);
			perkChoices.add(new Adaptable());
			perkChoices.add(new JackOfAllTrades());
			break;
		case DWARF:
			kin = "Dwarf";
			modMaxHitPoints(2);
			modBaseToughness(15);
			perkChoices.add(new DwarfResilient());
			perkChoices.add(new DwarfNotice());
			break;
		case ELF:
			kin = "Elf";
			modBaseMind(10);
			modBaseAgility(10);
			perkChoices.add(new GracefulFighter());
			perkChoices.add(new Nimble());
			break;
		case ORC:
			kin = "Orc";
			modBaseStrength(10);
			modBaseToughness(10);
			perkChoices.add(new Brutal());
			perkChoices.add(new Powerhouse());
			break;
		}
		this.kinChoice = kinChoice;
	}

	public void setAdventurer(AdventurerChoice adventurerChoice) {
		switch (adventurerChoice) {
		case WARRIOR:
			adventurer = new Warrior();
			addCombatChoice(new WarriorAttack());
			break;
		case ROGUE:
			adventurer = new Rogue();
			addCombatChoice(new RogueAttack());
			break;
		case MAGE:
			adventurer = new Mage();
			setSpellcaster(true);
			addCombatChoice(new MageAttack());
			break;
		}

		addCombatChoice(new Aim());
		addCombatChoice(new Defend());
		adventurer.addGeneralPerks(perkChoices);
		for (Perk p : adventurer.getPerkChoices()) {
			perkChoices.add(p);
		}

		adventurer.initEquipment(this);
		addToInventory(new Ration());
		addToInventory(new HealingPotion());

		currentHitPoints = getMaxHitPoints();
		currentStamina = getBaseStamina();
		this.adventurerChoice = adventurerChoice;
	}

	public void setArchetype(ArchetypeChoice archetypeChoice) {
		switch (archetypeChoice) {
		case ILLUSIONIST:
			archetype = new Illusionist();
			break;
		case OCCULTIST:
			archetype = new Occultist();
			break;
		case PYROMANCER:
			archetype = new Pyromancer();
			break;
		case ASSASSIN:
			archetype = new Assassin();
			break;
		case BURGLAR:
			archetype = new Burglar();
			break;
		case TROUBADOUR:
			archetype = new Troubadour();
			break;
		case BERSERKER:
			archetype = new Berserker();
			break;
		case KNIGHT:
			archetype = new Knight();
			break;
		case MANATARMS:
			archetype = new ManAtArms();
			break;
		}

		adventurerType = archetype.getName();

		// add archetype's signature ability
		addCombatChoice(archetype.getSignatureChoice());

		// add archetype's exclusive perks to player's perk choices
		for (Perk p : archetype.getPerkChoices()) {
			perkChoices.add(p);
		}
		this.archetypeChoice = archetypeChoice;
	}

	/**
	 * Increment player's Fatigue by parameter fatigue. Fatigue in excess of
	 * FATIGUE_MAX is dealt as damage to HP.
	 * 
	 * @param fatigue the amount of fatigue to take
	 * @return a string announcing damage taken from fatigue, unless there is none,
	 *         in which case the string is null
	 */
	public String takeFatigue(int fatigue) {
		int temp = this.fatigue;
		if (temp + fatigue > FATIGUE_MAX) {
			int damage = temp + fatigue - FATIGUE_MAX;
			this.fatigue = FATIGUE_MAX;
			takeDamage(damage);
			return name + " takes " + damage + " from fatigue. You need to eat and rest!";
		} else if (fatigue < 0) {
			this.fatigue += fatigue;
			if (this.fatigue < 0)
				this.fatigue = 0;
			return name + "'s fatigue is reduced by " + Math.abs(fatigue);
		} else {
			this.fatigue += fatigue;
			return "";
		}
	}

	public String receiveXP(int XP) {
		this.XP += XP;
		return name + " got " + XP + " XP!";
	}

	public String levelUp() {
		level++;
		modMaxHitPoints(1);
		XP -= REQUIRED_LEVEL_XP;
		return name + " has reached level " + level + "!";
	}

	public List<Choice> getCombatChoices() {
		return combatChoices;
	}

	public void addCombatChoice(Choice choice) {
		combatChoices.add(choice);
	}

	public void removeCombatChoice(Choice choice) {
		combatChoices.remove(choice);
	}

	public void addPerkChoice(Perk perk) {
		perkChoices.add(perk);
	}

	public void addChosenPerk(Perk perk) {
		chosenPerks.add(perk);
	}

	@Override
	public boolean isStrong() {
		int strTarget = getCurrentStrength();
		if (isPowerhouse())
			strTarget += ORC_STR_BUFF;

		if (GameScreen.generateRandom(1, 100) <= strTarget) {
			return true;
		}
		return false;
	}

	@Override
	public boolean notice() {
		int mindTarget = getCurrentMind();
		if (hasEyeForDetail)
			mindTarget += DWARF_NOTICE_BUFF;
		if (GameScreen.generateRandom(1, 100) <= mindTarget) {
			return true;
		}
		return false;
	}

	/**
	 * Only used by Mages for their spells. Spell attacks use Magic Damage instead
	 * of normal damage, don't trigger traps or riposte, can't cause Wounds,
	 * Burning, or Poison unless the spell specifically does so, and don't proc most
	 * other perks.
	 * 
	 * @param target      the target of the attack
	 * @param autoHit     whether the attack automatically hits
	 * @param autoCrit    whether the attack automatically crits
	 * @param isDodgeable whether the attack is dodgeable
	 * @param ignoresArmor whether the attack's damage ignores the target's Armor
	 */
	public String spellAttackTarget(Mob target, boolean autoHit, boolean autoCrit, boolean isDodgeable, boolean ignoresArmor) {
		StringBuilder str = new StringBuilder(name + " attacks " + target.getName() + "!");
		int damage = getCurrentMagicDamage();

		if (target.isProtected() && target.protector != this) { // prevents a Dominated protector from attacking itself
			str.append("\n" + getName() + " is protected by " + target.protector.getName() + "!");
			str.append(attackTarget(target.protector, autoHit, autoCrit, isDodgeable));
			return str.toString();
		}

		if (target.isHidden()) { // if target is hidden
			if (notice()) { // the attacker has a chance to notice them. If they notice, they attack.
				str.append("\n" + target.getName() + " is hidden, but " + name + " finds them!");
			} else { // if they don't notice, the attack is skipped
				str.append("\n" + target.getName() + " is hidden. " + name + " can't find them!");
				return str.toString();
			}
		}

		int rand = GameScreen.generateRandom(1, 100);
		if (isAccurate(rand) || autoHit) { // if the attack hits

			if (!target.doesDodge() || !isDodgeable) { // if the target

				if ((rand <= getCritRate() || autoCrit) && !target.isImmuneToCrit()) { // it's a critical hit
					str.append("\nCritical hit!");
					damage += damage * getCritDamage() / 100; // add crit damage to total
				}
				
				if (!ignoresArmor)
					damage -= target.getCurrentArmor(); // reduce damage by percentage of target's Armor
				
				if (damage < 0)
					damage = 0;
				damage += getPiercingDamage();
				str.append("\n" + target.takeDamage(damage));

				if (burningAttack && !target.isAgile())
					str.append("\n" + target.setBurning(true, burnDamage));
				if (poisonAttack && !target.isTough())
					str.append("\n" + target.setPoisoned(true, poisonDamage));

				if (target.getCurrentHitPoints() <= 0) {
					str.append("\n" + target.getName() + " is defeated!");
				}
			} else { // the target dodges
				str.append("\nBut " + target.getName() + " dodges!");
			}
		} else { // the attack doesn't hit
			str.append("\nBut the attack misses!");
		}

		if (isHidden())
			str.append("\n" + setHidden(false));

		return str.toString();
	}

	public String attackTarget(Mob target, boolean autoHit, boolean autoCrit, boolean isDodgeable) {
		StringBuilder str = new StringBuilder(name + " attacks " + target.getName() + "!");
		int damage = getCurrentDamage();

		if (target.isProtected() && target.protector != this) { // prevents a Dominated protector from attacking itself
			str.append("\n" + target.getName() + " is protected by " + target.protector.getName() + "!");
			str.append(attackTarget(target.protector, autoHit, autoCrit, isDodgeable));
			return str.toString();
		}

		if (target.isHidden()) { // if target is hidden
			if (notice()) { // the attacker has a chance to notice them. If they notice, they attack.
				str.append("\n" + target.getName() + " is hidden, but " + name + " finds them!");
			} else { // if they don't notice, the attack is skipped
				str.append("\n" + target.getName() + " is hidden. " + name + " can't find them!");
				return str.toString();
			}
		}

		if (target == knightDuelTarget)
			damage += KNIGHTDUELDAMAGEBUFF;

		if (hasBloodlust() && target.getWounded()) {
			str.append("\n" + name + "'s Bloodlust takes over!\n" + buffAccuracy(10, 0) + "\n" + buffCritRate(10, 0));
		}

		int rand = GameScreen.generateRandom(1, 100);
		if (isAccurate(rand) || autoHit) { // if the attack hits

			if (!target.doesDodge() || !isDodgeable) { // if the target doesn't dodge or the attack isn't dodgeable

				if ((rand <= getCritRate() || autoCrit) && !target.isImmuneToCrit()) { // it's a critical hit
					str.append("\nCritical hit!");
					damage += damage * getCritDamage() / 100; // add crit damage to total
				}
				damage -= target.getCurrentArmor(); // reduce damage by percentage of target's Armor
				if (damage < 0)
					damage = 0;
				damage += getPiercingDamage();
				str.append("\n" + target.takeDamage(damage));

				if (hasFollowUp())
					str.append("\n" + name + " can Follow Up! " + buffCritRate(FOLLOWUPCRITRATEBUFF, 1)); // if they
																											// have
																											// FollowUp,
																											// buff Crit

				if (target.getCurrentHitPoints() <= 0) {
					str.append("\n" + target.getName() + " is defeated!");
				} else {
					if (isWeaponBurning() && !target.isAgile()) // if the attack is Burning, target can Dodge to resist
						str.append("\n" + target.setBurning(true, burnDamage));
					if (isWeaponPoisoned() && !target.isTough()) // if the attack is Poisoned, target resists with
																	// Toughness
						str.append("\n" + target.setPoisoned(true, poisonDamage));
					if (GameScreen.generateRandom(1, 100) < getCurrentWoundRate()) { // see if the target is wounded
						str.append("\n"
								+ target.setWounded(true, GameScreen.generateRandom(1, 3), getCurrentWoundDamage()));
					}
				}

			} else { // the target dodges
				str.append("\nBut " + target.getName() + " dodges!");
				if (hasFeint()) {
					str.append("\n" + name + " Feints! " + buffAccuracy(FEINTACCURACYBUFF, 1) + "\n"
							+ buffCritRate(FEINTCRITRATEBUFF, 1));
				}
			}
		} else { // the attack doesn't hit
			str.append("\nBut the attack misses!");
			if (hasFeint()) {
				str.append("\n" + name + " Feints! " + buffAccuracy(FEINTACCURACYBUFF, 1) + "\n"
						+ buffCritRate(FEINTCRITRATEBUFF, 1));
			}
		}

		if (isHidden()) // if the attacker is hidden, they're not hidden anymore after the attack
			str.append("\n" + setHidden(false));

		if (target.hasTrap()) {
			str.append("\n" + target.triggerTrap(this));
		}

		if (target.hasFlameCloak()) { // if the target has Flame Cloak
			str.append("\n" + target.triggerFlameCloak(this));
		}

		if (target.isRiposteOn()) {
			GameScreen.log(target.attackTarget(this, false, false, true));
			if (!target.hasBladeStorm()) // if they don't have Blade Storm, turn off Riposte
				target.setRiposteOn(false);
		}

		return str.toString();
	}

	@Override
	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);

		if (getCurrentHitPoints() <= 0 && isSurvivor() && !isSurvivorUsed()) {
			currentHitPoints = 1;
			setSurvivorUsed(true);
			str += "\n" + name + " would be defeated, but survives!";
		}
		return str;
	}

	public boolean doesFlee() {
		int fleeTarget = getCurrentAgility();
		if (isNimble())
			fleeTarget += ELF_NIMBLE_BONUS;

		if (GameScreen.generateRandom(1, 100) <= fleeTarget) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isAccurate(int rand) {
		if (rand <= getCurrentAccuracy() + getAccuracyBuff()) {
			resetAccuracyBuff();
			return true;
		}
		incrementAccuracyBuff();
		return false;
	}

	public boolean doesDodge() {
		int dodgeTarget = getCurrentDodge();

		if (equippedWeapon != null)
			dodgeTarget += equippedWeapon.getDodgeMod();
		if (secondaryWeapon != null)
			dodgeTarget += secondaryWeapon.getDodgeMod();
		if (isGracefulFighter() && (isDualWielding || handSlotsFree == 1))
			dodgeTarget += ELF_GRACE_BONUS;

		if (GameScreen.generateRandom(1, 100) <= dodgeTarget) {
			return true;
		}
		return false;
	}

	public String checkRoundCounters() {
		String str = super.checkRoundCounters();

		for (Condition c : magicDamageConditions) {
			c.decrementRounds();
		}
		magicDamageConditions.removeIf(c -> (c.getRemainingRounds() < 0));

		if (weaponIsBurning) {
			weaponBurningRounds--;
			if (weaponBurningRounds <= 0) {
				weaponIsBurning = false;
				str += "\nThe flames die down on " + name + "'s weapon!";
			}
		}
		if (weaponIsPoisoned) {
			weaponPoisonRounds--;
			if (weaponPoisonRounds <= 0) {
				weaponIsPoisoned = false;
				str += "\nThe poison wears off on " + name + "'s weapon!";
			}
		}
		return str;
	}

	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		return "";
	} // these should never be called by Players

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		return "";
	}

	// removes/resets stats that last until the end of combat
	public void clearCombatLengthBuffs() {
		setSurvivorUsed(false);
	}

	// inventory methods

	public String addToInventory(Item item) {
		if (item.getType() == Item.Type.COINS) {
			return addCoins(item.getCount());
		} else if (item.getType() == Item.Type.KEY) {
			return addKey(item);
		}

		if (getInventoryTotal() == getMaxInventory()) {
			GameScreen.getCurrentRoom().addDroppedItem(item);
			return "You can't carry any more! Drop something to make room.";
		} else {
			if (inventory.keySet().contains(item.getName())) { // if item already exists in inventory
				inventory.get(item.getName()).incrementCount(); // get the item and increase its Count by 1
			} else { // if the item doesn't exist in inventory
				inventory.put(item.getName(), item); // put it in the inventory. Count always initializes at 1.
			}
			return item.getName() + " was added to your inventory.";
		}
	}

	public void removeFromInventory(String key) {
		inventory.get(key).decrementCount(); // decrement item's Count by 1
		if (inventory.get(key).getCount() == 0) { // if Count is 0
			inventory.remove(key); // remove from inventory
		}
	}

	public void removeFromInventory(Item item) {
		inventory.get(item.getName()).decrementCount(); // decrement item's Count by 1
		if (inventory.get(item.getName()).getCount() == 0) { // if Count is 0
			inventory.remove(item.getName()); // remove from inventory
		}
	}

	public int getInventoryTotal() {
		int count = 0;
		for (Map.Entry<String, Item> entry : inventory.entrySet()) {
			count += entry.getValue().getCount();
		}
		return count;
	}

	public void sellInventoryItem(Item item) {
		if (item.isEquipped() && item.getCount() == 1) {
			GameScreen.shopDescription.setText("You can't sell something you have equipped!");
		} else {
			if (item.getName().equals(STOLEN_GOODS)) {
				modCurse(3);
				GameScreen.shopDescription
						.setText("A heavy weight seems to press itself on your shoulders. What was that the "
								+ "adventurer said about a curse?");
			}
			addCoins(item.getCost() / 2); // sell items for half their original value
			removeFromInventory(item);
		}
	}

	public boolean inventoryContains(Item.Type type) {
		for (Item item : inventory.values()) {
			if (item.getType() == type)
				return true;
		}
		return false;
	}

	public String equipItem(Item item) {
		if (item.getType() == Item.Type.ARMOR) { // if it's Armor
			return equipArmor((Armor) item);
		} else if (item.getType() == Item.Type.WEAPON) { // if it's Weapon
			return equipHandWeapon((Weapon) item);
		} else { // it's Gear
			Gear gear = (Gear) item;
			if (gear.getWornType() == Gear.WornOn.HANDS) {
				return equipHandGear(gear);
			} else if (gear.getWornType() == Gear.WornOn.HEAD) {
				return equipHead(gear);
			} else { // it's MISC
				return equipMisc(gear);
			}
		}
	}

	public String unequipItem(Item item) {
		if (item.getType() == Item.Type.ARMOR) { // if it's Armor
			return unequipArmor();
		} else if (item.getType() == Item.Type.WEAPON) { // if it's Weapon
			return unequipWeapon((Weapon) item);
		} else { // it's Gear
			Gear gear = (Gear) item;
			if (gear.getWornType() == Gear.WornOn.HANDS) {
				return unequipHandGear();
			} else if (gear.getWornType() == Gear.WornOn.HEAD) {
				return unequipHead();
			} else { // it's MISC
				return unequipMisc();
			}
		}
	}

	public String equipArmor(Armor armor) {
		String str = "";
		if (equippedArmor != null)
			str += unequipArmor() + "\n";
		equippedArmor = armor;
		equippedArmor.setEquipped(true);
		equippedArmor.equipEffects(this);
		return str + "You equipped " + equippedArmor.getName() + ".";
	}

	public String unequipArmor() {
		if (equippedArmor != null) {
			String name = equippedArmor.getName();
			equippedArmor.unequipEffects(this);
			equippedArmor.setEquipped(false);
			equippedArmor = null;
			return "You unequipped " + name + ".";
		}
		return "You don't have Armor equipped!";
	}

	public String equipHead(Gear gear) {
		String str = "";
		if (equippedHeadGear != null)
			str += unequipHead() + "\n";
		equippedHeadGear = gear;
		equippedHeadGear.setEquipped(true);
		equippedHeadGear.equipEffects(this);
		return str + "You equipped " + equippedHeadGear.getName() + ".";
	}

	public String unequipHead() {
		if (equippedHeadGear != null) {
			String name = equippedHeadGear.getName();
			equippedHeadGear.unequipEffects(this);
			equippedHeadGear.setEquipped(false);
			equippedHeadGear = null;
			return "You unequipped " + name + ".";
		}
		return "You don't have Head Gear equipped!";
	}

	public String equipMisc(Gear gear) {
		String str = "";
		if (equippedMiscGear != null)
			str += unequipMisc() + "\n";
		equippedMiscGear = gear;
		equippedMiscGear.setEquipped(true);
		equippedMiscGear.equipEffects(this);
		return str + "You equipped " + equippedMiscGear.getName() + ".";
	}

	public String unequipMisc() {
		if (equippedMiscGear != null) {
			String name = equippedMiscGear.getName();
			equippedMiscGear.unequipEffects(this);
			equippedMiscGear.setEquipped(false);
			equippedMiscGear = null;
			return "You unequipped " + name + ".";
		}
		return "You don't have Misc Gear equipped!";
	}

	public String equipHandWeapon(Weapon weapon) {
		if (handSlotsFree < weapon.getHandsRequired()) {
			return ("You don't have enough hands free!\n" + weapon.getName() + " requires " + weapon.getHandsRequired()
					+ " hands." + "\nYou have " + handSlotsFree + " hands free.");
		}
		if (equippedWeapon != null) { // they have a free hand and are already wielding a weapon
			if (equippedWeapon.isLight() || weapon.isLight()) { // at least one of the weapons is Light
				String str = "";
				if (secondaryWeapon != null)
					str += unequipSecondaryWeapon() + "\n";
				secondaryWeapon = weapon;
				secondaryWeapon.setEquipped(true);
				secondaryWeapon.equipEffects(this);
				isDualWielding = true;
				handSlotsFree -= weapon.getHandsRequired();
				return (str + "You equipped " + secondaryWeapon.getName() + ". You are now dual wielding with "
						+ equippedWeapon.getName() + " and " + secondaryWeapon.getName() + ".");
			} else {
				return ("To dual wield weapons, at least one of them must be Light.");
			}
		} else { // they have a free hand and are not already wielding a weapon
			equippedWeapon = weapon;
			equippedWeapon.setEquipped(true);
			equippedWeapon.equipEffects(this);
			handSlotsFree -= weapon.getHandsRequired();
			return ("You equipped " + equippedWeapon.getName() + ".");
		}
	}

	public String unequipWeapon(Weapon weapon) {
		if (weapon == equippedWeapon) {
			String name = equippedWeapon.getName();
			equippedWeapon.unequipEffects(this);
			equippedWeapon.setEquipped(false);
			handSlotsFree += equippedWeapon.getHandsRequired();
			equippedWeapon = null;
			isDualWielding = false;

			// if there's a secondary weapon when you unequip your primary weapon
			if (secondaryWeapon != null) {
				// secondary weapon becomes your primary weapon
				Weapon tempWeapon = secondaryWeapon;
				unequipSecondaryWeapon();
				equipHandWeapon(tempWeapon);
				return "You unequipped " + name + ". " + tempWeapon.getName() + " is now your primary weapon.";
			}

			return "You unequipped " + name + ".";
		} else if (weapon == secondaryWeapon) {
			return unequipSecondaryWeapon();
		}
		return "";
	}

	public String unequipSecondaryWeapon() {
		if (secondaryWeapon != null) {
			String name = secondaryWeapon.getName();
			secondaryWeapon.unequipEffects(this);
			secondaryWeapon.setEquipped(false);
			handSlotsFree += secondaryWeapon.getHandsRequired();
			secondaryWeapon = null;
			isDualWielding = false;
			return "You unequipped " + name + ".";
		}
		return "You don't have a secondary weapon to unequip!";
	}

	public String equipHandGear(Gear gear) {
		// there is no hand gear equipped and you don't have a hand free
		if (handSlotsFree < gear.getHandsRequired() && equippedHandGear == null) {
			return ("You don't have enough hands free!\n" + gear.getName() + " requires " + gear.getHandsRequired()
					+ " hands." + "\nYou have " + handSlotsFree + " hands free.");
		}
		String str = "";
		if (equippedHandGear != null)
			str += unequipHandGear() + "\n";
		equippedHandGear = gear;
		equippedHandGear.setEquipped(true);
		equippedHandGear.equipEffects(this);
		handSlotsFree -= gear.getHandsRequired();
		return str + "You equipped " + equippedHandGear.getName() + ".";
	}

	public String unequipHandGear() {
		if (equippedHandGear != null) {
			String name = equippedHandGear.getName();
			equippedHandGear.unequipEffects(this);
			equippedHandGear.setEquipped(false);
			handSlotsFree += equippedHandGear.getHandsRequired();
			equippedHandGear = null;
			return "You unequipped " + name + ".";
		}
		return "You don't have Hand Gear to unequip!";
	}

	public int getInventorySize() {
		return inventory.size();
	}

	// modify statistic methods

	public String spendCoins(int coins) {
		if (isSwindler)
			coins -= 1;
		if (GameScreen.isStolenLootReturned)
			coins -= 1;
		if (coins < 1)
			coins = 1;
		this.coins -= coins;
		return "You spent " + coins + " coins. You now have: " + getCoins() + " coins.";
	}

	public String addCoins(int coins) {
		if (hasItchyPalm())
			coins += ITCHYPALMCOINS;
		this.coins += coins;
		return "You got " + coins + " coins.";
	}

	public String addKey(Item item) {
		keyRing.add(item.getName());
		return "You picked up a key!";
	}

	public void increaseMaxInventory(int buff) {
		maxInventory += buff;
	}

	public int getMaxInventory() {
		return maxInventory + baseStrength / 10;
	}

	public void modMaxHitPoints(int mod) {
		maxHitPoints += mod;
		currentHitPoints += mod;
	}

	public void modMaxStamina(int mod) {
		baseStamina += mod;
		currentStamina += mod;
	}

	public void modBaseToughness(int mod) {
		baseToughness += mod;
	}

	public void modBaseAgility(int mod) {
		baseAgility += mod;
	}

	public void modBaseMind(int mod) {
		baseMind += mod;
	}

	public void modBaseStrength(int mod) {
		baseStrength += mod;
	}

	public void modBaseAccuracy(int mod) {
		baseAccuracy += mod;
	}

	public void modBaseCritRate(int mod) {
		critRate += mod;
	}

	public void modBaseCritDamage(int mod) {
		critDamage += mod;
	}

	public void modBaseWoundRate(int mod) {
		woundRate += mod;
	}

	public String buffMagicDamage(int buff, int rounds) {
		magicDamageConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Magic Damage.";
	}

	public String debuffMagicDamage(int debuff, int rounds) {
		magicDamageConditions.add(new Condition(debuff, rounds));
		return name + " takes a -" + debuff + " to Magic Damage.";
	}

	// getters and setters

	public void setName(String name) {
		this.name = name;
	}

	public Archetype getArchetype() {
		return archetype;
	}

	@Override
	public String setBurning(boolean isBurning, int burningDamage) {
		if (isImmuneToBurning() || isImmuneBurningTemp() || isEvasive() || hasFlameWard()) {
			return "The flames burn brightly, but " + getName() + " is immune to burning!";
		}
		this.isBurning = isBurning;
		this.burningDamage = burningDamage;
		return getName() + " is burning!";
	}

	public int getBonusDamage() {
		int bonusDamage = getCurrentStrength() / 20;
		if (equippedWeapon != null && equippedWeapon.getHandsRequired() == 1 && handSlotsFree == 1) // if they're
																									// wielding with one
																									// hand and have
			// a hand free
			bonusDamage += EXTRA_HAND_DAMAGE_BONUS; // deal +1 damage
		return bonusDamage;
	}

	public int getBonusMagicDamage() {
		return getCurrentMind() / 20;
	}

	public int getBaseArmor() {
		if (equippedArmor != null) {
			return equippedArmor.getArmor();
		}
		return GameScreen.generateRandom(minArmor, maxArmor);
	}

	public int getMaxHitPoints() {
		return maxHitPoints + getCurrentToughness() / 10;
	}

	public int getBaseStamina() {
		return baseStamina + getStaminaItemBonuses();
	}

	@Override
	public int getBaseDamage() {
		if (isDualWielding) {
			int primaryDamage = equippedWeapon.getDamage();
			int secondaryDamage = secondaryWeapon.getDamage();
			if (primaryDamage > secondaryDamage)
				return primaryDamage;
			else
				return secondaryDamage;
		} else if (equippedWeapon != null) {
			return equippedWeapon.getDamage();
		}
		return GameScreen.generateRandom(minDamage, maxDamage);
	}

	public int getPiercingDamage() {
		return equippedWeapon.getPiercingDamage();
	}

	public int getBaseMagicDamage() {
		return GameScreen.generateRandom(minMagicDamage, maxMagicDamage);
	}

	public String getKin() {
		return kin;
	}

	public Adventurer getAdventurer() {
		return adventurer;
	}

	public int getCoins() {
		return coins;
	}

	public boolean isHoly() {
		return isHoly;
	}

	public void setHoly(boolean isHoly) {
		this.isHoly = isHoly;
	}

	public boolean isSpellcaster() {
		return isSpellcaster;
	}

	public void setSpellcaster(boolean isSpellcaster) {
		this.isSpellcaster = isSpellcaster;
	}

	public boolean isWeaponBurning() {
		return weaponIsBurning || weaponBurningPermanent;
	}

	public boolean isWeaponPoisoned() {
		return weaponIsPoisoned || weaponPoisonedPermanent;
	}

	public void resetWeaponBurning() {
		weaponIsBurning = false;
		burnDamage = 0;
		weaponBurningRounds = 0;
		weaponBurningPermanent = false;
	}

	public void resetWeaponPoisoned() {
		weaponIsPoisoned = false;
		poisonDamage = 0;
		weaponPoisonRounds = 0;
		weaponPoisonedPermanent = false;
	}

	/**
	 * 
	 * @param burnDamage       the damage the burn deals on hit
	 * @param burnRounds       the number of rounds the target burns for
	 * @param weaponBurnRounds how long the weapon is burning for. If permanent,
	 *                         this is ignored.
	 * @param isPermanent      whether or not the burn is permanent
	 * @return a string description of the result
	 */
	public String burnWeapon(int burnDamage, int weaponBurnRounds, boolean isPermanent) {
		if (weaponIsPoisoned) { // If weapon is poisoned, the burning overrides the poison
			weaponIsPoisoned = false;
			return name + "'s weapon is burning! The poison wears off!";
		} else if (weaponPoisonedPermanent) {
			return name + "'s weapon burns for a moment, but the oozing poison puts it out!";
		} else if (weaponBurningPermanent) {
			return name + "'s weapon is already burning!";
		} else {
			if (isPermanent)
				weaponBurningPermanent = isPermanent;
			else {
				weaponIsBurning = true;
				this.weaponBurningRounds = weaponBurnRounds;
			}
			this.burnDamage = burnDamage;
			return name + "'s weapon is burning!";
		}
	}

	/**
	 * 
	 * @param poisonDamage       the damage the poison deals on hit
	 * @param poisonRounds       the number of rounds the poison deals damage
	 * @param weaponPoisonRounds how long the weapon is poisoned for
	 * @param isPermanent        whether or not the poison is permanent
	 * @return a string description of the result
	 */
	public String poisonWeapon(int poisonDamage, int weaponPoisonRounds, boolean isPermanent) {
		if (weaponIsBurning) { // If weapon is burning, the poison overrides it
			weaponIsBurning = false;
			return name + "'s weapon is poisoned! The burning wears off!";
		} else if (weaponBurningPermanent) {
			return name + "'s weapon is poisoned, but the poison burns off!";
		} else if (weaponPoisonedPermanent) {
			return name + "'s weapon is already poisoned!";
		} else {
			if (isPermanent)
				weaponPoisonedPermanent = isPermanent;
			else {
				weaponIsPoisoned = true;
				this.weaponPoisonRounds = weaponPoisonRounds;
			}
			this.poisonDamage = poisonDamage;
			return name + "'s weapon is poisoned!";
		}
	}

	private int getArmorItemBonuses() {
		int itemBonus = 0;
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getArmorMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getArmorMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getArmorMod();
		return itemBonus;
	}

	private int getAgilityItemBonuses() {
		int itemBonus = 0;
		if (equippedArmor != null)
			itemBonus += equippedArmor.getAgilityMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getAgilityMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getAgilityMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getAgilityMod();
		return itemBonus;
	}

	private int getToughnessItemBonuses() {
		int itemBonus = 0;
		if (equippedArmor != null)
			itemBonus += equippedArmor.getToughnessMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getToughnessMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getToughnessMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getToughnessMod();
		return itemBonus;
	}

	private int getMindItemBonuses() {
		int itemBonus = 0;
		if (equippedArmor != null)
			itemBonus += equippedArmor.getMindMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getMindMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getMindMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getMindMod();
		return itemBonus;
	}

	private int getAccuracyItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getAccuracyMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getAccuracyMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getAccuracyMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getAccuracyMod();
		return itemBonus;
	}

	private int getDamageItemBonuses() {
		int itemBonus = 0;
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getDamageMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getDamageMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getDamageMod();
		return itemBonus;
	}

	private int getMagicDamageItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getMagicDamageMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getMagicDamageMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getMagicDamageMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getMagicDamageMod();
		return itemBonus;
	}

	private int getCritRateItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getCritRateMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getCritRateMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getCritRateMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getCritRateMod();
		return itemBonus;
	}

	private int getCritDamageItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getCritDamageMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getCritDamageMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getCritDamageMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getCritDamageMod();
		return itemBonus;
	}

	private int getWoundRateItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getWoundRateMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getWoundRateMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getWoundRateMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getWoundRateMod();
		return itemBonus;
	}

	private int getWoundDamageItemBonuses() {
		int itemBonus = 0;
		if (equippedWeapon != null)
			itemBonus += equippedWeapon.getWoundDamageMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getWoundDamageMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getWoundDamageMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getWoundDamageMod();
		return itemBonus;
	}

	private int getStaminaItemBonuses() {
		int itemBonus = 0;
		if (equippedArmor != null)
			itemBonus += equippedArmor.getStaminaMod();
		if (equippedHeadGear != null)
			itemBonus += equippedHeadGear.getStaminaMod();
		if (equippedHandGear != null)
			itemBonus += equippedHandGear.getStaminaMod();
		if (equippedMiscGear != null)
			itemBonus += equippedMiscGear.getStaminaMod();
		return itemBonus;
	}

	public int getCurrentArmor() {
		int currentArmor = super.getCurrentArmor() + getArmorItemBonuses();
		if (isResilient)
			currentArmor += DWARF_ARMOR_BUFF;
		if (knightDuelTarget != null)
			currentArmor += KNIGHTDUELARMORBUFF;
		return currentArmor;
	}

	public int getCurrentStrength() {
		return super.getCurrentStrength() - getCursePenalty();
	}

	public int getCurrentAgility() {
		return super.getCurrentAgility() + getAgilityItemBonuses() - getCursePenalty();
	}

	public int getCurrentToughness() {
		return super.getCurrentToughness() + getToughnessItemBonuses() - getCursePenalty();
	}

	public int getCurrentMind() {
		return super.getCurrentMind() + getMindItemBonuses() - getCursePenalty();
	}

	public int getCurrentAccuracy() {
		int currentAccuracy = super.getCurrentAccuracy() + getAccuracyItemBonuses() - getCursePenalty();
		if (equippedWeapon != null && equippedWeapon.getHandsRequired() == 1 && handSlotsFree == 1) // if they're
																									// wielding with one
																									// hand and have
			// a hand free
			currentAccuracy += 5; // get +5 accuracy
		return currentAccuracy;
	}

	public int getCurrentDamage() {
		return super.getCurrentDamage() + getDamageItemBonuses() + getBonusDamage();
	}

	public int getCurrentMagicDamage() {
		int currentMagicDamage = getBaseMagicDamage();
		for (Condition c : magicDamageConditions) {
			currentMagicDamage += c.getBuff();
		}
		return currentMagicDamage + getMagicDamageItemBonuses() + getBonusMagicDamage();
	}

	public int getCritRate() {
		int currentCritRate = super.getCritRate() + getCritRateItemBonuses();
		if (hasWoundedFury()) {
			currentCritRate += (getMaxHitPoints() - getCurrentHitPoints()) * getWoundedFuryCritBonus();
		}
		return currentCritRate;
	}

	public int getCritDamage() {
		return super.getCritDamage() + getCritDamageItemBonuses();
	}

	public int getCurrentWoundRate() {
		return super.getCurrentWoundRate() + getWoundRateItemBonuses();
	}

	public int getCurrentWoundDamage() {
		return super.getCurrentWoundDamage() + getWoundDamageItemBonuses();
	}

	public String getHandGearName() {
		if (equippedHandGear == null)
			return "NULL";
		return equippedHandGear.getName();
	}

	@Override
	public Item getItemDrop() {
		return null;
	}

	public boolean hasSecondAction() {
		return hasSecondAction;
	}

	public void setHasSecondAction(boolean hasSecondAction) {
		this.hasSecondAction = hasSecondAction;
	}

	public int getPoisonDamage() {
		return poisonDamage;
	}

	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

	public int getBurningDamage() {
		return burningDamage;
	}

	public void setBurningDamage(int burningDamage) {
		this.burningDamage = burningDamage;
	}

	public Set<String> getKeyRing() {
		return keyRing;
	}

	public Map<String, Item> getInventory() {
		return inventory;
	}

	public int getLevel() {
		return level;
	}

	public boolean isSwindler() {
		return isSwindler;
	}

	public void setSwindler(boolean isSwindler) {
		this.isSwindler = isSwindler;
	}

	public boolean isSurvivor() {
		return isSurvivor;
	}

	public void setSurvivor(boolean isSurvivor) {
		this.isSurvivor = isSurvivor;
	}

	public boolean isSurvivorUsed() {
		return survivorUsed;
	}

	public void setSurvivorUsed(boolean survivorUsed) {
		this.survivorUsed = survivorUsed;
	}

	public boolean hasIronhide() {
		return hasIronhide;
	}

	public void setHasIronhide(boolean hasIronhide) {
		this.hasIronhide = hasIronhide;
	}

	public boolean hasWoundedFury() {
		return hasWoundedFury;
	}

	public void setHasWoundedFury(boolean hasWoundedFury) {
		this.hasWoundedFury = hasWoundedFury;
	}

	public int getWoundedFuryCritBonus() {
		return woundedFuryCritBonus;
	}

	public void setWoundedFuryCritBonus(int woundedFuryCritBonus) {
		this.woundedFuryCritBonus = woundedFuryCritBonus;
	}

	public boolean hasToughRage() {
		return hasToughRage;
	}

	public void setHasToughRage(boolean hasToughRage) {
		this.hasToughRage = hasToughRage;
	}

	public boolean hasBloodlust() {
		return hasBloodlust;
	}

	public void setHasBloodlust(boolean hasBloodlust) {
		this.hasBloodlust = hasBloodlust;
	}

	public boolean hasKnightDuel() {
		return hasKnightDuel;
	}

	public void setHasKnightDuel(boolean hasKnightDuel) {
		this.hasKnightDuel = hasKnightDuel;
	}

	public Mob getKnightDuelTarget() {
		return knightDuelTarget;
	}

	public void setKnightDuelTarget(Mob knightDuelTarget) {
		this.knightDuelTarget = knightDuelTarget;
	}

	public boolean hasGuidedSmite() {
		return hasGuidedSmite;
	}

	public void setHasGuidedSmite(boolean hasGuidedSmite) {
		this.hasGuidedSmite = hasGuidedSmite;
	}

	public boolean hasFeint() {
		return hasFeint;
	}

	public void setHasFeint(boolean hasFeint) {
		this.hasFeint = hasFeint;
	}

	public boolean hasBladeWall() {
		return hasBladeWall;
	}

	public void setHasBladeWall(boolean hasBladeWall) {
		this.hasBladeWall = hasBladeWall;
	}

	public boolean hasBladeStorm() {
		return hasBladeStorm;
	}

	public void setHasBladeStorm(boolean hasBladeStorm) {
		this.hasBladeStorm = hasBladeStorm;
	}

	public boolean hasFollowUp() {
		return hasFollowUp;
	}

	public void setHasFollowUp(boolean hasFollowUp) {
		this.hasFollowUp = hasFollowUp;
	}

	public boolean hasCleave() {
		return hasCleave;
	}

	public void setHasCleave(boolean hasCleave) {
		this.hasCleave = hasCleave;
	}

	public boolean isEvasive() {
		return isEvasive;
	}

	public void setEvasive(boolean isEvasive) {
		this.isEvasive = isEvasive;
	}

	public boolean hasPhantom() {
		return hasPhantom;
	}

	public void setHasPhantom(boolean hasPhantom) {
		this.hasPhantom = hasPhantom;
	}

	public boolean isScout() {
		return isScout;
	}

	public void setScout(boolean isScout) {
		this.isScout = isScout;
	}

	public boolean hasAmbush() {
		return hasAmbush;
	}

	public void setHasAmbush(boolean hasAmbush) {
		this.hasAmbush = hasAmbush;
	}

	public boolean hasDeathblow() {
		return hasDeathblow;
	}

	public void setHasDeathblow(boolean hasDeathblow) {
		this.hasDeathblow = hasDeathblow;
	}

	public boolean hasItchyPalm() {
		return hasItchyPalm;
	}

	public void setHasItchyPalm(boolean hasItchyPalm) {
		this.hasItchyPalm = hasItchyPalm;
	}

	public boolean isExpertThief() {
		return isExpertThief;
	}

	public void setExpertThief(boolean isExpertThief) {
		this.isExpertThief = isExpertThief;
	}

	public boolean hasSkeletonKey() {
		return hasSkeletonKey;
	}

	public void setHasSkeletonKey(boolean hasSkeletonKey) {
		this.hasSkeletonKey = hasSkeletonKey;
	}

	public boolean isTrapExpert() {
		return isTrapExpert;
	}

	public void setTrapExpert(boolean isTrapExpert) {
		this.isTrapExpert = isTrapExpert;
	}

	public boolean hasBravado() {
		return hasBravado;
	}

	public void setHasBravado(boolean hasBravado) {
		this.hasBravado = hasBravado;
	}

	public boolean hasFootwork() {
		return hasFootwork;
	}

	public void setHasFootwork(boolean hasFootwork) {
		this.hasFootwork = hasFootwork;
	}

	public boolean hasUltimatePhantasm() {
		return hasUltimatePhantasm;
	}

	public void setHasUltimatePhantasm(boolean hasUltimatePhantasm) {
		this.hasUltimatePhantasm = hasUltimatePhantasm;
	}

	public boolean hasIncinerate() {
		return hasIncinerate;
	}

	public void setHasIncinerate(boolean hasIncinerate) {
		this.hasIncinerate = hasIncinerate;
	}

	public boolean hasFeedTheFlames() {
		return hasFeedTheFlames;
	}

	public void setHasFeedTheFlames(boolean hasFeedTheFlames) {
		this.hasFeedTheFlames = hasFeedTheFlames;
	}

	public boolean hasFlameWard() {
		return hasFlameWard;
	}

	public void setHasFlameWard(boolean hasFlameWard) {
		this.hasFlameWard = hasFlameWard;
	}

	public boolean hasDarkBargain() {
		return hasDarkBargain;
	}

	public void setHasDarkBargain(boolean hasDarkBargain) {
		this.hasDarkBargain = hasDarkBargain;
	}

	public boolean hasSacrifice() {
		return hasSacrifice;
	}

	public void setHasSacrifice(boolean hasSacrifice) {
		this.hasSacrifice = hasSacrifice;
	}

	public boolean isDualWielding() {
		return isDualWielding;
	}

	public void setDualWielding(boolean isDualWielding) {
		this.isDualWielding = isDualWielding;
	}

	public String getAdventurerType() {
		return adventurerType;
	}

	public List<Perk> getPerkChoices() {
		return perkChoices;
	}

	public boolean isResilient() {
		return isResilient;
	}

	public void setResilient(boolean isResilient) {
		this.isResilient = isResilient;
	}

	public boolean hasEyeForDetail() {
		return hasEyeForDetail;
	}

	public void setEyeForDetail(boolean hasEyeForDetail) {
		this.hasEyeForDetail = hasEyeForDetail;
	}

	public boolean isGracefulFighter() {
		return isGracefulFighter;
	}

	public void setGracefulFighter(boolean isGracefulFighter) {
		this.isGracefulFighter = isGracefulFighter;
	}

	public boolean isNimble() {
		return isNimble;
	}

	public void setNimble(boolean isNimble) {
		this.isNimble = isNimble;
	}

	public boolean isPowerhouse() {
		return isPowerhouse;
	}

	public void setPowerhouse(boolean isPowerhouse) {
		this.isPowerhouse = isPowerhouse;
	}

	public int getCurse() {
		return curse;
	}

	public void setCurse(int curse) {
		this.curse = curse;
	}

	public void modCurse(int mod) {
		curse += mod;
	}

	public int getCursePenalty() {
		return curse * CURSE_MULTIPLIER;
	}

	public int getAccuracyBuff() {
		return accuracyBuff;
	}

	public void resetAccuracyBuff() {
		accuracyBuff = 0;
	}

	public void incrementAccuracyBuff() {
		accuracyBuff += 5;
	}

	public CharacterSelectScreen.KinChoice getKinChoice() {
		return kinChoice;
	}

	public void setKinChoice(CharacterSelectScreen.KinChoice kinChoice) {
		this.kinChoice = kinChoice;
	}

	public CharacterSelectScreen.AdventurerChoice getAdventurerChoice() {
		return adventurerChoice;
	}

	public void setAdventurerChoice(CharacterSelectScreen.AdventurerChoice adventurerChoice) {
		this.adventurerChoice = adventurerChoice;
	}

	public CharacterSelectScreen.ArchetypeChoice getArchetypeChoice() {
		return archetypeChoice;
	}

	public void setArchetypeChoice(CharacterSelectScreen.ArchetypeChoice archetypeChoice) {
		this.archetypeChoice = archetypeChoice;
	}

	public void reset() {
		inventory.clear();
		maxInventory = BEGINNING_INVENTORY_MAX;
		level = 1;
		keyRing.clear();

		perkChoices.clear();
		combatChoices.clear();

		handSlotsFree = HAND_ITEM_SLOTS;

		equippedWeapon = null;
		secondaryWeapon = null;
		equippedArmor = null;
		equippedMiscGear = null;
		equippedHeadGear = null;
		equippedHandGear = null;
		isDualWielding = false;

		XP = 0;
		maxHitPoints = 10;
		currentHitPoints = 10;
		minArmor = 0;
		maxArmor = 0;
		baseAgility = 15;
		baseToughness = 15;
		baseMind = 15;
		baseStrength = 15;
		baseAccuracy = 80;
		critRate = 5;
		critDamage = 25; // expressed as a percentage of total damage done
		woundRate = 5;
		woundDamage = 1;
		minDamage = 1;
		maxDamage = 2; // damage without any weapons
		piercingDamage = 0;
		baseStamina = 4;
		currentStamina = 4;
		coins = 0;
		minMagicDamage = 3;
		maxMagicDamage = 5;
		fatigue = 0;

		armorConditions.clear();
		agilityConditions.clear();
		toughnessConditions.clear();
		mindConditions.clear();
		accuracyConditions.clear();
		magicDamageConditions.clear();
		damageConditions.clear();
		critRateConditions.clear();
		critDamageConditions.clear();
		woundRateConditions.clear();
		woundDamageConditions.clear();

		currentHitPoints = getMaxHitPoints();
		currentStamina = getBaseStamina();

		resetWeaponPoisoned();
		resetWeaponBurning();
		setHasSecondAction(false);
		weaponPoisonedPermanent = false;
		weaponBurningPermanent = false;

		setPoisoned(false);
		setBurning(false);
		setWounded(false);
		setRegenerating(false);

		kin = "";
		adventurerType = "";
		adventurer = null;
		archetype = null;

		kinChoice = null;
		adventurerChoice = null;
		archetypeChoice = null;

		setHoly(false);
		setSpellcaster(false);
		setSurvivor(false);
		setSurvivorUsed(false);
		setSwindler(false);

		setHasUltimatePhantasm(false);
		setHasDarkBargain(false);
		setHasDoppelgangers(false);
		setNumDoppelgangers(0);
		setHasFeedTheFlames(false);
		setHasFlameWard(false);
		setHasIncinerate(false);
		setHasSacrifice(false);

		setHasAmbush(false);
		setHasBravado(false);
		setHasDeathblow(false);
		setEvasive(false);
		setExpertThief(false);
		setHasFootwork(false);
		setHasItchyPalm(false);
		setHasPhantom(false);
		setScout(false);
		setHasSkeletonKey(false);
		setTrapExpert(false);

		setHasBladeWall(false);
		setHasBloodlust(false);
		setHasCleave(false);
		setHasFeint(false);
		setHasFollowUp(false);
		setHasGuidedSmite(false);
		setHasIronhide(false);
		setHasKnightDuel(false);
		setKnightDuelTarget(null);
		setHasToughRage(false);
		setHasWoundedFury(false);

		resetAccuracyBuff();

	}

}
