package adventurers;

import java.util.List;

import adventurers.perks.general.BuiltOx;
import adventurers.perks.general.Perk;
import adventurers.perks.general.Survivor;
import adventurers.perks.general.Swindler;
import mobs.Player;

public abstract class Adventurer {
	
	protected final int MIN_BUFF = 5;
	protected List<Perk> perkChoices;
	protected String name;
	
	protected int agilityIncreases;
	protected int toughnessIncreases;
	protected int mindIncreases;
	protected int accuracyIncreases;
	protected int strengthIncreases;
	
	protected int agilityBuff;
	protected int toughnessBuff;
	protected int mindBuff;
	protected int accuracyBuff;
	protected int strengthBuff;
	
	public enum StatChoice {
		ACCURACY, AGILITY, MIND, STRENGTH, TOUGHNESS
	}
	
	public String getName() {
		return name;
	}
	
	public void addGeneralPerks(List<Perk> perkChoices) {	
		perkChoices.add(new BuiltOx());
		perkChoices.add(new Survivor());
		perkChoices.add(new Swindler());
	}
	
	public abstract void initEquipment(Player player);
	
	public int getAccuracyIncrease() {
		int temp = accuracyBuff - (accuracyIncreases * 5);
		if (temp < MIN_BUFF)
			return MIN_BUFF;
		else 
			return temp;
	}
	
	public int getAgilityIncrease() {
		int temp = agilityBuff - (agilityIncreases * 5);
		if (temp < MIN_BUFF)
			return MIN_BUFF;
		else 
			return temp;
	}
	
	public int getMindIncrease() {
		int temp = mindBuff - (mindIncreases * 5);
		if (temp < MIN_BUFF)
			return MIN_BUFF;
		else 
			return temp;
	}
	
	public int getStrengthIncrease() {
		int temp = strengthBuff - (strengthIncreases * 5);
		if (temp < MIN_BUFF)
			return MIN_BUFF;
		else 
			return temp;
	}
	
	public int getToughnessIncrease() {
		int temp = toughnessBuff - (toughnessIncreases * 5);
		if (temp < MIN_BUFF)
			return MIN_BUFF;
		else 
			return temp;
	}
	
	public String getTempAccuracyIncrease(Player player) {
		return "Accuracy: " + player.getBaseAccuracy() + " -> " + (player.getBaseAccuracy() + getAccuracyIncrease())
				+ "\nCrit Rate: " + player.getBaseCritRate() + " -> " + (player.getBaseCritRate() + getAccuracyIncrease());
	}
	
	public String getTempAgilityIncrease(Player player) {
		int baseDodge = 0;
		if (player.isSkirmisher())
			baseDodge = player.getBaseAgility() * 2 / 3;
		else 
			baseDodge = player.getBaseAgility()/2;
		
		int newDodge = 0;
		if (player.isSkirmisher())
			newDodge = (player.getBaseAgility() + getAgilityIncrease()) * 2 / 3;
		else 
			newDodge = (player.getBaseAgility() + getAgilityIncrease())/2;
		
		return "Agility: " + player.getBaseAgility() + " -> " + (player.getBaseAgility() + getAgilityIncrease())
				+ "\nDodge: " + baseDodge + " -> " + newDodge;
	}
	
	public String getTempMindIncrease(Player player) {
		return "Mind: " + player.getBaseMind() + " -> " + (player.getBaseMind() + getMindIncrease())
				+ "\nBonus Magic Damage: " + player.getBaseMind()/20 + " -> " + (player.getBaseMind() + getMindIncrease())/20;
	}
	
	public String getTempStrengthIncrease(Player player) {
		return "Strength: " + player.getBaseStrength() + " -> " + (player.getBaseStrength() + getStrengthIncrease())
				+ "\nBonus Melee Damage: " + player.getBaseStrength()/20 + " -> " + (player.getBaseStrength() + getStrengthIncrease())/20
				+ "\nMax Inventory: " + player.getMaxInventory() + " -> " + (10 + (player.getBaseStrength() + getStrengthIncrease())/10);
	}
	
	public String getTempToughnessIncrease(Player player) {
		return "Toughness: " + player.getBaseToughness() + " -> " + (player.getBaseToughness() + getToughnessIncrease())
				+ "\nBonus Hit Points: " + player.getBaseToughness()/10 + " -> " + (player.getBaseToughness() + getToughnessIncrease())/10;
	}
	
	public void applyStatBonus(Player player, StatChoice statChoice) {
		switch (statChoice) {
		case ACCURACY:
			player.modBaseAccuracy(getAccuracyIncrease());
			accuracyIncreases++;
			break;
		case AGILITY:
			player.modBaseAgility(getAgilityIncrease());
			agilityIncreases++;
			break;
		case MIND:
			player.modBaseMind(getMindIncrease());
			mindIncreases++;
			break;
		case STRENGTH:
			player.modBaseStrength(getStrengthIncrease());
			strengthIncreases++;
			break;
		case TOUGHNESS:
			player.modBaseToughness(getToughnessIncrease());
			toughnessIncreases++;
			break;
		default:
			break;
		}
	}

	public List<Perk> getPerkChoices() {
		return perkChoices;
	}

}


