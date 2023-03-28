package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class ShieldSpell extends Choice {

	private int dodgeBuff;
	private int rounds;
	
	public ShieldSpell() {
		name = "Shield";
		dodgeBuff = 20;
		rounds = 3;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.hasDarkBargain()) {
			String str = "The dark powers increase the power you channel...";
			str += "\n" + player.buffDodge(dodgeBuff * 2, rounds);
			GameScreen.setLogger(str);
			player.setHasDarkBargain(false);
		} else {
			GameScreen.setLogger(player.buffDodge(dodgeBuff, rounds));
		}
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + dodgeBuff + " chance to Dodge for " + rounds + " rounds";
	}

}
