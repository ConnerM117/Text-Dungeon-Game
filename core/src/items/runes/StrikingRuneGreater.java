package items.runes;

import com.textdungeon.game.GameScreen;

public class StrikingRuneGreater extends StrikingRune {

	public StrikingRuneGreater() {
		name = "Greater Striking Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		maxDamageBuff = GameScreen.generateRandom(2, 4);
		description = "The weapon's maximum damage is increased by " + maxDamageBuff;
	}
}
