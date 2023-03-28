package items.runes;

import com.textdungeon.game.GameScreen;

public class WeightRuneGreater extends WeightRune {

	public WeightRuneGreater() {
		name = "Greater Weight Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		damageBuff = 1 + GameScreen.generateRandom(1, 3);
		accuracyDebuff = 14 + GameScreen.generateRandom(1, 7);
		description = "The weapon's weight is increased, reducing Accuracy by " + accuracyDebuff + ", but increasing "
				+ "damage by " + damageBuff;
	}
	
}
