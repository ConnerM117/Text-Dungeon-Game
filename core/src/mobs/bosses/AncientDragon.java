package mobs.bosses;

import java.util.List;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.armor.DragonhideArmor;
import items.gear.DragonScale;
import mobs.Mob;

public class AncientDragon extends Boss {

	public AncientDragon() {
		super();
		name = "Ancient Dragon";
		XP = 5;
		maxHitPoints = 16;
		currentHitPoints = maxHitPoints;
		minArmor = 4;
		maxArmor = 6;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 90;
		baseMind = 80;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 9;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 1;
		woundDamage = 1;
		baseStamina = 3;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " takes to the sky and performs a flyby attack!\n" + buffDodge(20, 1) + "\n"
				+ attackTarget(target, false, false, true);
	}

	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " exhales a column of flame!";

		setBurningAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetBurningAttack();

		return str;
	}

	public String staminaActionTwo(Mob target, List<Mob> mobs) {
		String str = name + " creates a cloud of smoke!";
		if (!target.isTough()) {
			str += target.getName() + " isn't tough enough to endure it!\n" + target.debuffAccuracy(20, 3) + "\n"
					+ target.debuffAgility(10, 3) + "\n" + target.takeDamage(getCurrentDamage() / 2);
		} else {
			str += target.getName() + " manages to push through without ill effects!";
		}
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		int rand = GameScreen.generateRandom(1, 2);
		if (rand == 1)
			return new DragonhideArmor();
		else
			return new DragonScale();
	}
}
