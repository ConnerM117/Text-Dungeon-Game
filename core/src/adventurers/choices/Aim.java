package adventurers.choices;

import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.Player;

public class Aim extends Choice {

	private int accuracyBonus;
	private int critRateBonus;
	private int rounds;

	public Aim() {
		name = "Aim";
		accuracyBonus = 20;
		critRateBonus = 50;
		rounds = 2;
		requiresTarget = false;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = "";

		if (player.hasDarkBargain()) {
			str += "The dark powers increase the power you channel...\n"
					+ player.buffAccuracy(accuracyBonus * 2, rounds) + "\n"
					+ player.buffCritRate(critRateBonus * 2, rounds);
			player.setHasDarkBargain(false);
		} else {
			str += player.buffAccuracy(accuracyBonus, rounds) + "\n" + player.buffCritRate(critRateBonus, rounds);
		}

		GameScreen.setLogger(str);

		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + accuracyBonus + " Accuracy and +" + critRateBonus + " Crit Rate for " + rounds + " rounds.";
	}

}
