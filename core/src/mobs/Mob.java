package mobs;

import java.util.*;

import com.textdungeon.game.GameScreen;

import core.*;
import items.Item;

public abstract class Mob {
	
	//statistics
	protected String name;
	protected int XP;
	protected int maxHitPoints;
	protected int currentHitPoints;
	protected int minArmor;
	protected int maxArmor;
	protected int tempHP;
	protected int baseAgility;
	protected int baseToughness;
	protected int baseMind;
	protected int baseStrength;
	protected int baseAccuracy;
	protected int minDamage;
	protected int maxDamage;
	protected int piercingDamage;
	protected int critRate;
	protected int critDamage;
	protected int woundRate;
	protected int woundDamage;
	protected int baseStamina;
	protected int currentStamina;
	protected boolean poisonAttack;
	protected int poisonDamage;
	protected boolean burningAttack;
	protected int burnDamage;
	
	//condition trackers
	protected List<Condition> armorConditions;
	protected List<Condition> agilityConditions;
	protected List<Condition> toughnessConditions;
	protected List<Condition> mindConditions;
	protected List<Condition> strengthConditions;
	protected List<Condition> accuracyConditions;
	protected List<Condition> damageConditions;
	protected List<Condition> critRateConditions;
	protected List<Condition> critDamageConditions;
	protected List<Condition> woundRateConditions;
	protected List<Condition> woundDamageConditions;
	protected List<Condition> dodgeConditions;
	
	//Damage Over Time variables
	protected boolean isPoisoned;
	protected int poisonedDamage; //damage you take from poison
	protected boolean isBurning;
	protected int burningDamage; //damage you take from burning
	protected boolean isWounded;
	protected int woundedDamage; //damage you take from being wounded
	protected boolean isRegenerating;
	protected int regenerateValue;
	protected int regenerateRounds;
	
	//conditions
	protected boolean hasTrap;
	protected int trapDamage;
	protected boolean isProtected;
	protected Mob protector;
	protected int protectedRoundCounter;
	protected boolean isInvincible;
	protected int invincibleRoundCounter;
	protected boolean isHidden;
	protected int hiddenRoundCounter;
	protected final int HIDDENDAMAGEBUFF = 2;
	protected final int HIDDENAGILITYBUFF = 20;
	
	protected boolean riposteOn; //only players should ever set this to true
	protected int riposteRoundCounter;
	protected boolean hasBladeStorm;
	
	protected boolean hasFlameCloak;
	protected int flameCloakRoundCounter;
	protected final int FLAMECLOAKBURNDAMAGE = 1;
	protected final int FLAMECLOAKBURNDURATION = 3;
	
	protected boolean isSkirmisher; //put this logic in doesDodge

	private boolean hasDoppelgangers;
	private int numDoppelgangers;
	
	protected boolean isStolenFrom;
	
	//immunities
	protected boolean immuneToBurning;
	protected boolean immuneToPoison;
	protected boolean immuneToWounded;
	protected boolean isImmuneToCrit;
	
	private boolean isImmuneBurningTemp;
	private int immuneBurningRounds;
	private boolean isImmunePoisonTemp;
	private int immunePoisonRounds;
	
	/**
	 * Only increased when this mob dodges an attack, and resets to 0 when it is hit.
	 * Prevents strings of attacks from player that are all dodged.
	 */
	private int dodgeDebuff;
	
	//constructor
	
	public Mob () {
		name = "";
		armorConditions = new ArrayList<>();
		agilityConditions = new ArrayList<>();
		toughnessConditions = new ArrayList<>();
		mindConditions = new ArrayList<>();
		strengthConditions = new ArrayList<>();
		accuracyConditions = new ArrayList<>();
		damageConditions = new ArrayList<>();
		critRateConditions = new ArrayList<>();
		critDamageConditions = new ArrayList<>();
		woundRateConditions = new ArrayList<>();
		woundDamageConditions = new ArrayList<>();
		dodgeConditions = new ArrayList<>();
	}
	
	//protected methods
	
	/**
	 * Searches a list of Mobs to see if any are currently Protected.
	 * @param mobs the list of mobs to search
	 * @return true if any mobs are currently Protected, false otherwise
	 */
	protected boolean mobsAreProtected(List<Mob> mobs) {
		for (Mob m : mobs) {
			if (m.isProtected())
				return true;
		}
		return false;
	}
	
	protected boolean isProtected() {
		return this.isProtected;
	}

	//public methods
	
	public abstract Item getItemDrop();
	
	public abstract String specialAction(Mob target, List<Mob> mobs);
	
	public abstract String staminaAction(Mob target, List<Mob> mobs);
	
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//mob has 3 choices: attack, special move, or stamina move
		int choice = 0;
			
		if (getCurrentStamina() == 0) {
			choice = GameScreen.generateRandom(1, 3);
		} else { //it has Stamina, then allow for stamina move
			choice = GameScreen.generateRandom(1, 4);
		}
		
		if (choice <= 2) { //choice is 1 or 2
			return attackTarget(mob, false, false, true);
		} else if (choice == 3) {
			return specialAction(mob, mobs);
		} else { //choice is 4
			spendStamina(1);
			return staminaAction(mob, mobs);
		}
	}
	
	public boolean isAccurate(int rand) {
		if (rand <= getCurrentAccuracy()) {
			return true;
		}
		return false;
	}
	
	public boolean isAccurate() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentAccuracy()) {
			return true;
		}
		return false;
	}
	
	public boolean doesDodge() {
		int dodgeTarget = getCurrentDodge() - getDodgeDebuff();
		
		if (GameScreen.generateRandom(1, 100) <= dodgeTarget) {
			debuffDodge();
			return true;
		}
		resetDodgeDebuff();
		return false;
	}
	
	public boolean isAgile() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentAgility()) {
			return true;
		}
		return false;
	}
	
	public boolean isStrong() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentStrength()) {
			return true;
		}
		return false;
	}
	
	public boolean isTough() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentToughness()) {
			return true;
		}
		return false;
	}
	
	public boolean isMindful() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentMind()) {
			return true;
		}
		return false;
	}
	
	public boolean notice() {
		if (GameScreen.generateRandom(1, 100) <= getCurrentMind()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param target the target of the attack
	 * @param autoHit if the attack should automatically hit
	 * @param autoCrit if the attack should automatically crit
	 * @param isDodgeable if the attack is dodgeable or not
	 * @return 
	 */
	public String attackTarget(Mob target, boolean autoHit, boolean autoCrit, boolean isDodgeable) {
		StringBuilder str = new StringBuilder(name + " attacks " + target.getName() + "!");
		int damage = getCurrentDamage();
		
		if (target.isProtected() && target.protector != this) { //prevents a Dominated protector from attacking itself
			str.append("\n" + getName() + " is protected by " + target.protector.getName() + "!\n");
			str.append(attackTarget(target.protector, autoHit, autoCrit, isDodgeable));
			return str.toString();
		}
		
		if (target.isHidden()) { //if target is hidden
			if (notice()) { //the attacker has a chance to notice them. If they notice, they attack.
				str.append("\n" + target.getName() + " is hidden, but " + name + " finds them!");
			} else { //if they don't notice, the attack is skipped
				str.append("\n" + target.getName() + " is hidden. " + name + " can't find them!");
				return str.toString();
			}
		}

		int rand = GameScreen.generateRandom(1, 100);
		if (isAccurate(rand) || autoHit) { // if the attack hits
			
			if (!target.doesDodge() || !isDodgeable) { // if the target doesn't dodge or the attack isn't dodgeable
				
				if (target.hasDoppelgangers() && GameScreen.generateRandom(0, target.getNumDoppelgangers()) > 0) {
					target.numDoppelgangers--;
					if (getNumDoppelgangers() == 0)
						setHasDoppelgangers(false);
					str.append("\nAn illusory doppelganger takes the hit! (" + target.getNumDoppelgangers() + " remaining)");
				} else {
				
					if ((rand <= getCritRate() || autoCrit) && !target.isImmuneToCrit()) { //it's a critical hit and target can be critically hit
						str.append("\nCritical hit!");
						damage += damage * getCritDamage()/100; //add crit damage to total
					}
					damage -= target.getCurrentArmor(); //reduce damage by target's Armor
					if (damage < 0)
						damage = 0;
					damage += getPiercingDamage();
					str.append("\n" + target.takeDamage(damage));

					if (target.getCurrentHitPoints() <= 0) {
						str.append("\n" + target.getName() + " is defeated!");
					} else {
						if (burningAttack && !target.isAgile()) //if the attack hits and is a burning attack, the target has a chance to dodge to resist burning
							str.append("\n" + target.setBurning(true, burnDamage)); //if they don't resist, burn for random rounds from min to max
						
						if (poisonAttack && !target.isTough()) //if the attack hits and is a poison attack, the target has a chance to resist the poison
							str.append("\n" + target.setPoisoned(true, poisonDamage)); //if they don't resist, poison for random rounds from min to max
						
						if (GameScreen.generateRandom(1, 100) < getCurrentWoundRate()) { //see if the target is wounded
							str.append("\n" + target.setWounded(true, GameScreen.generateRandom(1, 3), getCurrentWoundDamage())); //if they don't resist, bleeding for random rounds from 1 to 3
						}
					}
				}
				
			} else { //the target dodges
				str.append("\nBut " + target.getName() + " dodges!");
			}
		} else { //the attack doesn't hit
			str.append("\nBut the attack misses!");
		}
		
		if (isHidden())
			str.append("\n" + setHidden(false));
		
		if (target.hasTrap()) {
			str.append("\n" + target.triggerTrap(this));
		}
		
		if (target.hasFlameCloak()) { //if the target has Flame Cloak
			str.append("\n" + target.triggerFlameCloak(this));
		}
		
		if (target.isRiposteOn()) {
			GameScreen.log(target.attackTarget(this, false, false, true));
			if (!target.hasBladeStorm()) //if they don't have Blade Storm, turn off Riposte
				target.setRiposteOn(false);
		}
		
		return str.toString();
	}
	
	protected String triggerFlameCloak(Mob target) {
		if (!target.isAgile()) { //you have a chance to dodge to resist burning
			setBurning(true, FLAMECLOAKBURNDAMAGE);
			return target.getName() + " gets too close to " + getName() + "and catches fire!";
		} else { //you dodge
			return target.getName() + " gets away from " + getName() + " without catching fire!";
		}
	}
	
	protected String triggerTrap(Mob target) {
		String str = getName() + "'s trap goes off!";
		if (!target.isAgile()) { //if the mob doesn't dodge
			str += "\n" + target.getName() + " is caught by the trap!";
			takeDamage(getTrapDamage());
		} else {
			str += "\nBut " + target.getName() + " avoids the trap!";
		}
		setHasTrap(false);
		return str;
	}

	//modify statistics

	public String removeAllBuffs() {
		agilityConditions.removeIf(c -> (c.getBuff() > 0));
		toughnessConditions.removeIf(c -> (c.getBuff() > 0));
		mindConditions.removeIf(c -> (c.getBuff() > 0));
		strengthConditions.removeIf(c -> (c.getBuff() > 0));
		accuracyConditions.removeIf(c -> (c.getBuff() > 0));
		damageConditions.removeIf(c -> (c.getBuff() > 0));
		critRateConditions.removeIf(c -> (c.getBuff() > 0));
		critDamageConditions.removeIf(c -> (c.getBuff() > 0));
		woundRateConditions.removeIf(c -> (c.getBuff() > 0));
		woundDamageConditions.removeIf(c -> (c.getBuff() > 0));
		dodgeConditions.removeIf(c -> (c.getBuff() > 0));
		return name + " has all buffs removed!";
	}
	
	public String removeAllDebuffs() {
		agilityConditions.removeIf(c -> (c.getBuff() < 0));
		toughnessConditions.removeIf(c -> (c.getBuff() < 0));
		mindConditions.removeIf(c -> (c.getBuff() < 0));
		strengthConditions.removeIf(c -> (c.getBuff() < 0));
		accuracyConditions.removeIf(c -> (c.getBuff() < 0));
		damageConditions.removeIf(c -> (c.getBuff() < 0));
		critRateConditions.removeIf(c -> (c.getBuff() < 0));
		critDamageConditions.removeIf(c -> (c.getBuff() < 0));
		woundRateConditions.removeIf(c -> (c.getBuff() < 0));
		woundDamageConditions.removeIf(c -> (c.getBuff() < 0));
		dodgeConditions.removeIf(c -> (c.getBuff() < 0));
		return name + " has all debuffs removed!";
	}
	
	public String takeDamage(int damage) {
		if (isInvincible) {
			return getName() + " is invincible and takes no damage!";
		} else {
			int temp = 0;
			if (tempHP > 0) { //if it has tempHP
				temp = tempHP - damage; //figure the difference between tempHP and damage
				if (temp < 0) { //if there's excess damage
					currentHitPoints += temp; //deal it to currentHP
					tempHP = 0; //and reduce tempHP to 0
				} else { //if there's not excess damage
					tempHP -= damage; //reduce tempHP by damage
				}
			} else { //if there's not any tempHP
				currentHitPoints -= damage; //deduct damage from currentHP
			}
			return name + " takes " + damage + " damage.";
		}
	}
	
	public String regenerate(int heal, int rounds) {
		this.isRegenerating = true;
		if (regenerateValue < heal)
			regenerateValue = heal;
		if (regenerateRounds < rounds) 
			regenerateRounds = rounds;
		return name + " is regenerating " + heal + " hit points for " + rounds + " rounds!";
	}
	
	public String healDamage (int heal) {
		if (getCurrentHitPoints() == getMaxHitPoints()) {
			return name + " isn't wounded!";
		} else {
			currentHitPoints = getCurrentHitPoints() + heal;
			if (getCurrentHitPoints() > getMaxHitPoints())
				currentHitPoints = getMaxHitPoints();
			return name + " healed " + heal + " hit points.";
		}
	}
	
	public String getStatistics () {
		String str = name + ", HP: " + getCurrentHitPoints() + "/" + getMaxHitPoints();
		if (getTempHP() > 0)
			str += " (" + getTempHP() + " Shield)";
		if (isHidden())
			str += " (Hidden)";
		return str;
	}
	
	public String getStatistics (int counter) {
		String str = counter + ") " + name + ", HP: " + getCurrentHitPoints() + "/" + getMaxHitPoints();
		if (getTempHP() > 0)
			str += " (" + getTempHP() + " Shield)";
		if (isHidden())
			str += " (Hidden)";
		return str;
	}
	
	public String spendStamina(int stamina) {
		currentStamina = getCurrentStamina() - stamina;
		return name + " spent " + stamina + " stamina.";
	}
	
	public String healStamina(int stamina) {
		currentStamina = getCurrentStamina() + stamina;
		if (getCurrentStamina() > getBaseStamina()) {
			currentStamina = getBaseStamina();
		}
		return name + " regained " + stamina + " stamina.";
	}
	
	public String checkRoundCounters() {
		String str = "";
		for (Condition c : armorConditions) { 
			c.decrementRounds();
		}
		armorConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : agilityConditions) { 
			c.decrementRounds();
		}
		agilityConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : toughnessConditions) { 
			c.decrementRounds();
		}
		toughnessConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : mindConditions) { 
			c.decrementRounds();
		}
		mindConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : strengthConditions) { 
			c.decrementRounds();
		}
		strengthConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : accuracyConditions) { 
			c.decrementRounds();
		}
		accuracyConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : damageConditions) { 
			c.decrementRounds();
		}
		damageConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : critRateConditions) { 
			c.decrementRounds();
		}
		critRateConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : critDamageConditions) { 
			c.decrementRounds();
		}
		critDamageConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : woundRateConditions) { 
			c.decrementRounds();
		}
		woundRateConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : woundDamageConditions) { 
			c.decrementRounds();
		}
		woundDamageConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		for (Condition c : dodgeConditions) { 
			c.decrementRounds();
		}
		dodgeConditions.removeIf(c -> (c.getRemainingRounds() < 0));
		
		if (riposteRoundCounter >= 1) { //checks riposte buff and decrements/removes if required
			riposteRoundCounter--;
		} else {
			setRiposteOn(false);
		}
		
		if (isPoisoned) { //checks poisoned debuff and decrements/removes if required
			if (isTough()) {
				isPoisoned = false;
				str += "\n" + name + " recovers from poison!";
			} else {
				str += "\n" + name + " takes damage from poison!";
				str += "\n" + takeDamage(poisonedDamage);
			}
		} 
		
		if (isBurning) { //checks burning debuff and decrements/removes if required
			if (isAgile()) {
				isBurning = false;
				str += "\n" + name + " is no longer burning!";
			} else {
				str += "\n" + name + " takes damage from burning!";
				str += "\n" + takeDamage(burningDamage);
			}
		} 
		
		if (isWounded) { //checks wounded debuff and decrements/removes if required
			if (isTough()) {
				isWounded = false;
				str += "\n" + name + " is no longer bleeding!";
			} else {
				str += "\n" + name + " takes damage from bleeding!";
				str += "\n" + takeDamage(woundedDamage);
			}
		} 

		if (protectedRoundCounter >= 1) { //checks protected and decrements/removes if required
			protectedRoundCounter--;
		} else {
			setProtected(false);
		}
		if (flameCloakRoundCounter >= 1) { //checks flame cloak and decrements/removes if required
			flameCloakRoundCounter--;
		} else {
			setHasFlameCloak(false, 0);
		}
		if (invincibleRoundCounter >= 1) { //checks invincible and decrements/removes if required
			invincibleRoundCounter--;
		} else {
			setInvincible(false);
		}
		if (hiddenRoundCounter >= 1) { //checks hidden and decrements/removes if required
			hiddenRoundCounter--;
		} else {
			setHidden(false);
		}
		
		if (regenerateRounds >= 1) {
			str += "\n" + name + " regenerates " + regenerateValue + " hit points!";
			str += "\n" + healDamage(regenerateValue);
			regenerateRounds--;
		} else {
			setRegenerating(false);
		}
		
		if (immunePoisonRounds >= 1) { //checks temporary immunity to poison and decrements/removes
			immunePoisonRounds--;
		} else {
			setImmunePoisonTemp(false);
		}
		if (immuneBurningRounds >= 1) { //checks temporary immunity to burning and decrements/removes
			immuneBurningRounds--;
		} else {
			setImmunePoisonTemp(false);
		}
		
		return str;
	}

	// buff/debuff methods that add conditions to condition lists
	
	public String buffArmor (int buff, int rounds) {
		armorConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Armor for " + rounds + " rounds.";
	}
	
	public String buffAgility (int buff, int rounds) {
		agilityConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Agility for " + rounds + " rounds.";
	}
	
	public String buffToughness (int buff, int rounds) {
		toughnessConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Toughness for " + rounds + " rounds.";
	}
	
	public String buffMind (int buff, int rounds) {
		mindConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Mind for " + rounds + " rounds.";
	}
	
	public String buffStrength (int buff, int rounds) {
		strengthConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Strength for " + rounds + " rounds.";
	}
	
	public String buffAccuracy (int buff, int rounds) {
		accuracyConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Accuracy for " + rounds + " rounds.";
	}
	
	public String buffDamage (int buff, int rounds) {
		damageConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Damage for " + rounds + " rounds.";
	}
	
	public String buffCritRate (int buff, int rounds) {
		critRateConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Crit Rate for " + rounds + " rounds.";
	}
	
	public String buffCritDamage (int buff, int rounds) {
		critDamageConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Crit Damage for " + rounds + " rounds.";
	}
	
	public String buffWoundRate (int buff, int rounds) {
		woundRateConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Wound Rate for " + rounds + " rounds.";
	}
	
	public String buffWoundDamage (int buff, int rounds) {
		woundDamageConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Wound Damage for " + rounds + " rounds.";
	}
	
	public String buffDodge (int buff, int rounds) {
		dodgeConditions.add(new Condition(buff, rounds));
		return name + " gains a +" + buff + " to Dodge for " + rounds + " rounds.";
	}
	
	public String debuffArmor (int debuff, int rounds) {
		armorConditions.add(new Condition(-debuff, rounds));
		String str = name + " takes a -" + debuff + " to Armor for " + rounds + " rounds.";
		if (getCurrentArmor() < 0) {
			str += "\n" + name + "'s Armor can't get any lower!";
		}
		return str;
	}
	
	public String debuffAgility (int debuff, int rounds) {
		agilityConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Agility for " + rounds + " rounds.";
	}
	
	public String debuffToughness (int debuff, int rounds) {
		toughnessConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Toughness for " + rounds + " rounds.";
	}
	
	public String debuffMind (int debuff, int rounds) {
		mindConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Mind for " + rounds + " rounds.";
	}
	
	public String debuffStrength (int debuff, int rounds) {
		strengthConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Strength for " + rounds + " rounds.";
	}
	
	public String debuffAccuracy (int debuff, int rounds) {
		accuracyConditions.add(new Condition(debuff, rounds));
		String str = name + " takes a -" + debuff + " to Accuracy for " + rounds + " rounds.";
		if (getCurrentAccuracy() < 30) {
			str += "\n" + name + "'s Accuracy can't get any lower!";
		}
		return str;
	}
	
	public String debuffDamage (int debuff, int rounds) {
		damageConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Damage for " + rounds + " rounds.";
	}
	
	public String debuffCritRate (int debuff, int rounds) {
		critRateConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Crit Rate for " + rounds + " rounds.";
	}
	
	public String debuffCritDamage (int debuff, int rounds) {
		critDamageConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Crit Damage for " + rounds + " rounds.";
	}
	
	public String debuffWoundRate (int debuff, int rounds) {
		woundRateConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Wound Rate for " + rounds + " rounds.";
	}
	
	public String debuffWoundDamage (int debuff, int rounds) {
		woundDamageConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Wound Damage for " + rounds + " rounds.";
	}
	
	public String debuffDodge (int debuff, int rounds) {
		dodgeConditions.add(new Condition(-debuff, rounds));
		return name + " takes a -" + debuff + " to Dodge for " + rounds + " rounds.";
	}
	
	//getters and setters

	public String getName() {
		return name;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	
	public int getBaseArmor() {
		return GameScreen.generateRandom(minArmor, maxArmor);
	}
	
	public int getBaseStamina() {
		return baseStamina;
	}

	public int getCurrentStamina() {
		return currentStamina;
	}

	public int getBaseAgility() {
		return baseAgility;
	}

	public int getBaseToughness() {
		return baseToughness;
	}
	
	public int getBaseMind() {
		return baseMind;
	}
	
	public int getBaseStrength() {
		return baseStrength;
	}
	
	public int getBaseAccuracy() {
		return baseAccuracy;
	}
	
	public int getBaseDamage() {
		return GameScreen.generateRandom(minDamage, maxDamage);
	}
	
	public int getPiercingDamage() {
		return piercingDamage;
	}
	
	public int getBaseCritRate() {
		return critRate;
	}
	
	public int getBaseCritDamage() {
		return critDamage;
	}
	
	public int getWoundRate() {
		return woundRate;
	}
	
	public int getWoundDamage() {
		return woundDamage;
	}
	
	public int getCurrentArmor() {
		int currentArmor = getBaseArmor();
		for (Condition c : armorConditions) {
			currentArmor += c.getBuff();
		}
		if (currentArmor < 0) {
			return 0;
		}
		return currentArmor;
	}
	
	public int getCurrentAgility() {
		int currentAgility = baseAgility;
		for (Condition c : agilityConditions) {
			currentAgility += c.getBuff();
		}
		if (isHidden()) currentAgility += HIDDENAGILITYBUFF;
		return currentAgility;
	}

	public int getCurrentToughness() {
		int currentToughness = baseToughness;
		for (Condition c : toughnessConditions) {
			currentToughness += c.getBuff();
		}
		return currentToughness;
	}
	
	public int getCurrentMind() {
		int currentMind = baseMind;
		for (Condition c : mindConditions) {
			currentMind += c.getBuff();
		}
		return currentMind;
	}
	
	public int getCurrentStrength() {
		int currentStrength = baseStrength;
		for (Condition c : mindConditions) {
			currentStrength += c.getBuff();
		}
		return currentStrength;
	}

	public int getCurrentAccuracy() {
		int currentAccuracy = baseAccuracy;
		for (Condition c : accuracyConditions) {
			currentAccuracy += c.getBuff();
		}
		if (currentAccuracy < 30) {
			return 30;
		}
		return currentAccuracy;
	}

	public int getCurrentDamage() {
		int currentDamage = getBaseDamage();
		for (Condition c : damageConditions) {
			currentDamage += c.getBuff();
		}
		if (isHidden()) currentDamage += HIDDENDAMAGEBUFF;
		return currentDamage;
	}
	
	public int getCritRate() {
		int currentCritRate = critRate;
		for (Condition c : critRateConditions) {
			currentCritRate += c.getBuff();
		}
		return currentCritRate;
	}
	
	public int getCritDamage() {
		int currentCritDamage = critDamage;
		for (Condition c : critDamageConditions) {
			currentCritDamage += c.getBuff();
		}
		return currentCritDamage;
	}
	
	public int getCurrentWoundRate() {
		int currentWoundRate = woundRate;
		for (Condition c : woundRateConditions) {
			currentWoundRate += c.getBuff();
		}
		return currentWoundRate;
	}
	
	public int getCurrentWoundDamage() {
		int currentWoundDamage = woundDamage;
		for (Condition c : woundDamageConditions) {
			currentWoundDamage += c.getBuff();
		}
		return currentWoundDamage;
	}
	
	public int getCurrentDodge() {
		int currentDodge = getCurrentAgility()/4;
		if (isSkirmisher)
			currentDodge = getCurrentAgility() / 3;

		for (Condition c : dodgeConditions) {
			currentDodge += c.getBuff();
		}
		
		return currentDodge;
	}
	
	public int getTempHP() {
		return tempHP;
	}
	
	public String setTempHP(int tempHP) {
		this.tempHP = tempHP;
		return name + " gains a " + tempHP + " hit point shield!";
	}
	
	public boolean isRiposteOn() {
		return riposteOn;
	}

	public void setRiposteOn(boolean riposteOn) {
		this.riposteOn = riposteOn;
	}
	
	public void useRiposte() {
		riposteOn = true;
		riposteRoundCounter = 1;
	}
	
	public boolean hasBladeStorm() {
		return hasBladeStorm;
	}
	
	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}
	
	public String setPoisoned(boolean isPoisoned, int poisonedDamage) {
		if (isImmuneToPoison() || isImmunePoisonTemp()) {
			return getName() + " is immune to poison!";
		}
		this.isPoisoned = isPoisoned;
		this.poisonedDamage = poisonedDamage;
		return getName() + " is poisoned!";
	}
	
	public void setBurning(boolean isBurning) {
		this.isBurning = isBurning;
	}
	
	public String setBurning(boolean isBurning, int burningDamage) {
		if (isImmuneToBurning() || isImmuneBurningTemp()) {
			return "The flames burn brightly, but " + getName() + " is immune to burning!";
		}
		this.isBurning = isBurning;
		this.burningDamage = burningDamage;
		return getName() + " is burning!";
	}
	
	public void setWounded(boolean isWounded) {
		this.isWounded = isWounded;
		woundedDamage = 0;
	}
	
	public String setWounded(boolean isWounded, int woundedRoundCounter, int bleedingDamage) {
		if (isImmuneToWounded()) {
			return "The strike threatens " + getName() + "'s vitality, but they're immune to bleeding!";
		}
		this.isWounded = isWounded;
		this.woundedDamage = bleedingDamage;
		return getName() + " is bleeding for " + woundedRoundCounter + " round(s)!";
	}
	
	public boolean isPoisoned() {
		return isPoisoned;
	}
	
	public boolean isBurning() {
		return isBurning;
	}
	
	public boolean getWounded() {
		return isWounded;
	}
	
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	
	public void setProtected(boolean isProtected, Mob protector, int protectedRoundCounter) {
		this.isProtected = isProtected;
		this.protectedRoundCounter = protectedRoundCounter;
		this.protector = protector;
	}
	
	public void resetProtected() {
		isProtected = false;
		protector = null;
		protectedRoundCounter = 0;
	}
	
	public Mob getProtector() {
		return protector;
	}
	
	public int getProtectedRoundCounter() {
		return protectedRoundCounter;
	}
	
	public boolean isHidden() {
		return isHidden;
	}
	
	public String setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		return  name + " is no longer hidden!";
	}
	
	public int getHiddenRoundCounter() {
		return hiddenRoundCounter;
	}
	
	public String setHidden(boolean isHidden, int hiddenRoundCounter) {
		this.isHidden = isHidden;
		if (isHidden && this.hiddenRoundCounter < hiddenRoundCounter) { //keep it from resetting round counter to a lower value
			this.hiddenRoundCounter = hiddenRoundCounter;
		}
		return name + " is hidden!";
	}
	
	protected boolean hasTrap() {
		return hasTrap;
	}
	
	public void setHasTrap(boolean hasTrap) {
		this.hasTrap = hasTrap;
	}
	
	public void setHasTrap(boolean hasTrap, int trapDamage) {
		this.hasTrap = hasTrap;
		this.trapDamage = trapDamage;
	}
	
	public int getTrapDamage() {	
		return trapDamage;
	}
	
	public void setTrapDamage(int trapDamage) {
		this.trapDamage = trapDamage;
	}
	
	public boolean isImmuneToBurning() {
		return immuneToBurning;
	}
	
	private boolean isImmuneToPoison() {
		return immuneToPoison;
	}
	
	public void setImmuneToBurning(boolean immuneToBurning) {
		this.immuneToBurning = immuneToBurning;
	}

	public void setImmuneToPoison(boolean immuneToPoison) {
		this.immuneToPoison = immuneToPoison;
	}

	public boolean isImmuneBurningTemp() {
		return isImmuneBurningTemp;
	}

	public void setImmuneBurningTemp(boolean isImmuneBurningTemp) {
		this.isImmuneBurningTemp = isImmuneBurningTemp;
		immuneBurningRounds = 0;
	}
	
	public String setImmuneBurningTemp(boolean isImmuneBurningTemp, int rounds) {
		this.isImmuneBurningTemp = isImmuneBurningTemp;
		if (this.isImmuneBurningTemp && this.immuneBurningRounds < rounds) { //keep it from resetting round counter to a lower value
			this.immuneBurningRounds = rounds;
		}
		setBurning(false);
		return getName() + " is immune to burning for " + immunePoisonRounds + " round(s)!";
	}

	public boolean isImmunePoisonTemp() {
		return isImmunePoisonTemp;
	}

	public void setImmunePoisonTemp(boolean isImmunePoisonTemp) {
		this.isImmunePoisonTemp = isImmunePoisonTemp;
		immunePoisonRounds = 0;
	}
	
	public String setImmunePoisonTemp(boolean isImmunePoisonTemp, int rounds) {
		this.isImmunePoisonTemp = isImmunePoisonTemp;
		if (this.isImmunePoisonTemp && this.immunePoisonRounds < rounds) { //keep it from resetting round counter to a lower value
			this.immunePoisonRounds = rounds;
		}
		setPoisoned(false);
		return getName() + " is immune to poison for " + immunePoisonRounds + " round(s)!";
	}
	
	public boolean isImmuneToWounded() {
		return immuneToWounded;
	}
	
	public void setImmuneToWounded(boolean immuneToWounded) {
		this.immuneToWounded = immuneToWounded;
	}
	
	protected void setRegenerating(boolean isRegenerating) {
		this.isRegenerating = isRegenerating;
	}
	
	public boolean isInvincible() {
		return isInvincible;
	}
	
	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}
	
	public String setInvincible(boolean isInvincible, int invincibleRoundCounter) {
		this.isInvincible = isInvincible;
		if (this.isInvincible && this.invincibleRoundCounter < invincibleRoundCounter) { //keep it from resetting round counter to a lower value
			this.invincibleRoundCounter = invincibleRoundCounter;
		}
		return getName() + " is invincible for " + invincibleRoundCounter + " rounds!";
	}
	
	public boolean getBurningAttack() {
		return burningAttack;
	}
	
	public void resetBurningAttack() {
		burningAttack = false;
		burnDamage = 0;
	}
	
	public void setBurningAttack(boolean burningAttack, int burnDamage) {
		this.burningAttack = burningAttack;
		this.burnDamage = burnDamage;
	}
	
	public boolean getPoisonAttack() {
		return poisonAttack;
	}
	
	public void resetPoisonAttack() {
		poisonAttack = false;
		poisonDamage = 0;
	}
	
	/**
	 * Allows the mob to have a poison attack when set to true.
	 * @param poisonAttack whether or not the attack has poison.
	 * @param poisonDamage the damage dealt by the poison.
	 * @param poisonRounds the number of rounds the poison lasts.
	 */
	public void setPoisonAttack(boolean poisonAttack, int poisonDamage) {
		this.poisonAttack = poisonAttack;
		this.poisonDamage = poisonDamage;
	}
	
	public boolean isSkirmisher() {
		return isSkirmisher;
	}
	
	public void setSkirmisher(boolean isSkirmisher) {
		this.isSkirmisher = isSkirmisher;
	}

	public boolean isStolenFrom() {
		return isStolenFrom;
	}
	
	public void setStolenFrom(boolean isStolenFrom) {
		this.isStolenFrom = isStolenFrom;
	}
	
	public boolean isImmuneToCrit() {
		return isImmuneToCrit;
	}

	public void setIsImmuneToCrit(boolean isImmuneToCrit) {
		this.isImmuneToCrit = isImmuneToCrit;
	}

	public boolean hasFlameCloak() {
		return hasFlameCloak;
	}
	
	public void setHasFlameCloak(boolean hasFlameCloak, int flameCloakRoundCounter) {
		this.hasFlameCloak = hasFlameCloak;
		this.flameCloakRoundCounter = flameCloakRoundCounter;
	}
	
	public boolean hasDoppelgangers() {
		return hasDoppelgangers;
	}

	public void setHasDoppelgangers(boolean hasDoppelgangers) {
		this.hasDoppelgangers = hasDoppelgangers;
	}

	public int getNumDoppelgangers() {
		return numDoppelgangers;
	}

	public void setNumDoppelgangers(int numDoppelgangers) {
		this.numDoppelgangers = numDoppelgangers;
	}
	
	public int getXP() {
		return XP;
	}

	public int getDodgeDebuff() {
		return dodgeDebuff;
	}

	public void resetDodgeDebuff() {
		dodgeDebuff = 0;
	}
	
	public void debuffDodge() {
		dodgeDebuff -= 10;
	}
}
