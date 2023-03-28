package items.runes;

import com.textdungeon.game.GameScreen;

public class KeenRuneGreater extends KeenRune {

	public KeenRuneGreater() {
		name = "Greater Keen Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;

		minDamageBuff = GameScreen.generateRandom(3, 5);
		description = "Raise the weapon's minimum damage by " + minDamageBuff;
	}
}
