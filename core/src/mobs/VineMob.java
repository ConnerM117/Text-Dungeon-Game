package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class VineMob extends Mob {

	private String giantMushroom;
	private boolean isProtecting;
	private Mob protectedMob;
	
	public VineMob() {
		super();
		name = "Thorny Vines";
		XP = 0;
		maxHitPoints = 3;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 40;
		baseToughness = 50;
		baseMind = 10;
		baseAccuracy = 70;
		minDamage = 2;
		maxDamage = 4;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 0;
		woundDamage = 1;
		baseStamina = 2;
		currentStamina = baseStamina;
		
		giantMushroom = new GiantMushroom().getName();
	}
	
	@Override
	public Item getItemDrop() {
		return null;
	}

	public String takeDamage(int damage) {
		String str = super.takeDamage(damage);
		if (isProtecting && getCurrentHitPoints() <= 0) { //if it is protecting something when it dies
			protectedMob.resetProtected(); //ensure the protection ends
		}
		return str;
	}
	
	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " slashes with long thorns!\n" + buffWoundRate(100, 0) + "\n" + attackTarget(target, false, false, true);
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		int choice = 0;
		
		for (int i = 0; i < mobs.size(); i++) {
			if (mobs.get(i).getName().equals(giantMushroom)) {
				choice = i;
				break;
			}
		}
		
		mobs.get(choice).setProtected(true, this, 2);
		isProtecting = true;
		protectedMob = mobs.get(choice);

		return name + " protects " + mobs.get(choice).getName() + "!";
	}
	
	@Override
	public String getCombatChoice(Mob mob, List<Mob> mobs) {
		//mob has 3 choices: attack, special move, or stamina move; choices are weighted toward Protecting (stamina move)
		int choice = 0;
		boolean isValid = false;
			
		while (!isValid) {
			if (getCurrentStamina() == 0) {
				choice = GameScreen.generateRandom(1, 3);
			} else { //it has Stamina, then allow for stamina move
				choice = GameScreen.generateRandom(1, 4);
			}
			
			if (choice <= 2) {
				isValid = true;
			} else if (choice == 3 && getTempHP() == 0) { 
				isValid = true;
			} else if (choice == 4 && mobs.size() > 1 && !mobsAreProtected(mobs)) { //only protect an ally if there is an ally to protect and something isn't already protected (prevents protection loops/chains)
				isValid = true;
			}
		}
		
		if (choice == 1) {
			return attackTarget(mob, false, false, true);
		} else if (choice == 2) {
			return specialAction(mob, mobs);
		} else { //choice is 3 or 4
			spendStamina(1);
			return staminaAction(mob, mobs);
		}
	}

}
