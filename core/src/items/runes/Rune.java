package items.runes;

import com.textdungeon.game.GameScreen;

import items.Item;
import items.weapons.Weapon;

public abstract class Rune extends Item {
	
	public abstract void modifyWeapon(Weapon weapon);
	
	public abstract void revertChanges(Weapon weapon);
	
	@Override
	public String getStatistics() {
		return getDescription() + "\nPrice: " + cost;
	}
	
	public static Rune getRandRune() {
		if (GameScreen.generateRandom(1, 2) == 1)
			return getRandGreaterRune();
		return getRandLesserRune();
	}
	
	public static Rune getRandRuneWeighted(int floor) {
		if (GameScreen.generateRandom(1, 10) >= floor)
			return getRandLesserRune();
		return getRandGreaterRune();
	}
	
	public static Rune getRandLesserRune() {
		int rand = GameScreen.generateRandom(1, 10);
		switch(rand) {
		case 1: return new ChannelingRune();
		case 2: return new FlameRune();
		case 3: return new GuidingRune();
		case 4: return new KeenRune();
		case 5: return new ParryRune();
		case 6: return new PiercingRune();
		case 7: return new StrikingRune();
		case 8: return new VenomRune();
		case 9: return new WeightRune();
		case 10: return new WoundingRune();
		default: return new ChannelingRune();
		}
	}
	
	public static Rune getRandGreaterRune() {
		int rand = GameScreen.generateRandom(1, 10);
		switch(rand) {
		case 1: return new ChannelingRuneGreater();
		case 2: return new FlameRuneGreater();
		case 3: return new GuidingRuneGreater();
		case 4: return new KeenRuneGreater();
		case 5: return new ParryRuneGreater();
		case 6: return new PiercingRuneGreater();
		case 7: return new StrikingRuneGreater();
		case 8: return new VenomRuneGreater();
		case 9: return new WeightRuneGreater();
		case 10: return new WoundingRuneGreater();
		default: return new ChannelingRuneGreater();
		}
	}
	
}
