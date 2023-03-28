package mobs;

import java.util.List;

import com.textdungeon.game.GameEvent;
import com.textdungeon.game.GameScreen;

import items.Item;
import items.weapons.EldritchHorn;

public class EldritchBeast extends Mob {
	
	public EldritchBeast(int counter) {
		super();
		name = "Eldritch Beast " + counter;
		XP = 2;
		maxHitPoints = 12;
		currentHitPoints = maxHitPoints;
		minArmor = 3;
		maxArmor = 5;
		tempHP = 0;
		baseAgility = 20;
		baseToughness = 80;
		baseMind = 50;
		baseAccuracy = 70;
		minDamage = 4;
		maxDamage = 6;
		piercingDamage = 1;
		critRate = 15;
		critDamage = 50;
		woundRate = 10;
		woundDamage = 1;
		baseStamina = 1;
		currentStamina = baseStamina;
	}
	
	public String specialAction(Mob target, List<Mob> mobs) {
		String str = target.getName() + " is sickened by the presence of " + name + "!\n";
		
		setPoisonAttack(true, 2);
		str += attackTarget(target, false, false, true);
		resetPoisonAttack();
		
		return str;
	}
	
	public String staminaAction(Mob target, List<Mob> mobs) {
		String str = name + " pauses time and acts twice!";
		
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
		return new EldritchHorn();
	}

}
