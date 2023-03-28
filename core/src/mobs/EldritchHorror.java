package mobs;

import java.util.List;

import com.textdungeon.game.GameEvent;
import com.textdungeon.game.GameScreen;

import items.Item;
import items.consumables.EldritchEye;

public class EldritchHorror extends Mob {
	
	public EldritchHorror(int counter) {
		super();
		name = "Eldritch Horror " + counter;
		maxHitPoints = 20;
		currentHitPoints = maxHitPoints;
		minArmor = 2;
		maxArmor = 4;
		tempHP = 0;
		baseAgility = 30;
		baseToughness = 80;
		baseMind = 70;
		baseAccuracy = 70;
		minDamage = 5;
		maxDamage = 8;
		piercingDamage = 0;
		critRate = 10;
		critDamage = 25;
		woundRate = 5;
		woundDamage = 1;
		baseStamina = 2;
		currentStamina = baseStamina;
	}

	public String specialAction(Mob target, List<Mob> mobs) {
		return name + " extends its horrifying presence!\n" + target.debuffAccuracy(20, 2) + "\n" + target.debuffAgility(20, 2);
	}
	
	//attack with a diseased bite that also has a chance to poison the target
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " warps time and acts twice!";
		
		GameScreen.eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				GameScreen.setLogger(getCombatChoice(target, mobs));
			}
		});
		
		GameScreen.eventHandler.addEvent(new GameEvent() {
			@Override
			public void runEvent() {
				GameScreen.setLogger(getCombatChoice(target, mobs));
			}
		});
		
		return str;
	}

	@Override
	public Item getItemDrop() {
		return new EldritchEye();
	}
}
