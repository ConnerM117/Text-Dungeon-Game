package mobs;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;

public class GiantMushroom extends Mob {

	public GiantMushroom() {
		super();
		name = "Giant Mushroom";
		XP = 3;
		maxHitPoints = 15;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 2;
		tempHP = 0;
		baseAgility = 0;
		baseToughness = 80;
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
	}
	
	@Override
	public Item getItemDrop() {
		return null;
	}

	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " lets loose a burst of poison spores!\n";
		
		setPoisonAttack(true, 1);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		spendStamina(1);
		GameScreen.mobsToAdd.add(new VineMob());
		return "Vine-like tendrils burst forth from the ground!";
	}

}
