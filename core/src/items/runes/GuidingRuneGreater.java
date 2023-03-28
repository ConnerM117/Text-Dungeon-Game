package items.runes;

import com.textdungeon.game.GameScreen;

public class GuidingRuneGreater extends GuidingRune {

	public GuidingRuneGreater() {
		name = "Greater Guiding Rune";
		count = 1;
		cost = 20;
		type = Type.RUNE;
		
		critDamageBuff = 20 + GameScreen.generateRandom(5, 30);
		critRateBuff = 14 + GameScreen.generateRandom(1, 5);
		description = "The weapon guides your hand to your foe's vulnerabilities, granting +" + critRateBuff + " to Crit Rate "
				+ "and +" + critDamageBuff + " to Crit Damage";
	}
}
