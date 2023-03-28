package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Empower extends Choice {

	private int damageBuff;
	private int critRateBuff;
	private int rounds;
	
	public Empower() {
		name = "Empower";
		damageBuff = 2;
		critRateBuff = 10;
		rounds = 3;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.hasDarkBargain()) {
			String str = "The dark powers increase the power you channel...";
			str += "\n" + player.buffMagicDamage(damageBuff * 2, rounds);
			str += "\n" + player.buffCritRate(critRateBuff * 2, rounds);
			player.setHasDarkBargain(false);
			GameScreen.setLogger(str);
		} else {
			String str = player.buffMagicDamage(damageBuff, rounds);
			str += "\n" + player.buffCritRate(critRateBuff, rounds);
			GameScreen.setLogger(str);
		}
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + damageBuff + " Magic Damage and +" + critRateBuff + " Crit Rate for " + rounds + " rounds";
	}

}
