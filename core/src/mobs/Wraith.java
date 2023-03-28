package mobs;

import java.util.List;

import items.Item;

public class Wraith extends Mob {

	private int invincibleRounds;
	private int debuff;
	private int debuffRounds;
	
	public Wraith() {
		super();
		name = "Wraith";
		XP = 4;
		maxHitPoints = 10;
		currentHitPoints = maxHitPoints;
		minArmor = 0;
		maxArmor = 0;
		tempHP = 0;
		baseAgility = 60;
		baseToughness = 100;
		baseMind = 60;
		baseAccuracy = 70;
		minDamage = 3;
		maxDamage = 6;
		piercingDamage = 0;
		critRate = 5;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
		
		invincibleRounds = 2;
		debuff = 20;
		debuffRounds = 3;
		
		immuneToWounded = true;
		immuneToPoison = true;
	}
	
	@Override
	public String takeDamage(int damage) {
		damage = damage/2; //wraith is incorporeal and damage it takes is halved
		return super.takeDamage(damage);
	}
	
	@Override
	public Item getItemDrop() {
		return null;
	}

	@Override
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = name + " roars, and the shadows grow deeper...\n";
		if (!target.isMindful()) {
			str += target.getName() + " is filled with unspeakable dread!\n";
			str += target.debuffAccuracy(debuff, debuffRounds) + "\n" + target.debuffAgility(debuff, debuffRounds)
				+ "\n" + target.debuffMind(debuff, debuffRounds);
		} else {
			str += target.getName() + " is able to resist the evil's power over the mind!";
		}
		return str;
	}

	@Override
	public String staminaAction(Mob target, List<Mob> mobs) {
		return name + " becomes completely incorporeal!\n" + setInvincible(true, invincibleRounds);
	}

}
