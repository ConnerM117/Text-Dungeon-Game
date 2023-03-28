package items.consumables;

import com.textdungeon.game.GameScreen;

import items.Item;

public abstract class Consumable extends Item {
	
	public Consumable() {
		name = "";
		count = 1;
		cost = 0;
		isEquippable = false;
		type = Type.CONSUMABLE;
	}
	
	public String getStatistics() {
		return description  + "\nPrice: " + cost;
	}
	
	public static Consumable getRandConsumable() {
		int rand = GameScreen.generateRandom(1, 16);
		switch (rand) {
		case 1: return new Antidote();
		case 2: return new BurningPitch();
		case 3: return new FlaskOfWater();
		case 4: return new GoblinMoonshine();
		case 5: return new HealingPotion();
		case 6: return new Laudanum();
		case 7: return new PoisonVial();
		case 8: return new Whetstone();
		case 9: return new Bandage();
		case 10: return new Lockpicks();
		case 11: return new SketchyMushroom();
		case 12: return new Rope();
		case 13: return new RatBag();
		case 14: return new LiquidCourage();
		case 15: return new Ration();
		case 16: return new Chalk();
		default: return new Bandage();
		}
	}

}
