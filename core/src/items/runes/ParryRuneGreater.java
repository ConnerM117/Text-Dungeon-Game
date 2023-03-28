package items.runes;

import com.textdungeon.game.GameScreen;

public class ParryRuneGreater extends ParryRune {

	public ParryRuneGreater() {
		name = "Greater Parry Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		dodgeBuff = 9 + GameScreen.generateRandom(1, 4);
		description = "Increase your Dodge chance by " + dodgeBuff;
	}
}
