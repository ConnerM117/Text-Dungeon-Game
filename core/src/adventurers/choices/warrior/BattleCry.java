package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class BattleCry extends Choice {
	
	private int critRateBonus;
	private int accuracyBonus;
	private int rounds;
	
	public BattleCry() {
		critRateBonus = 10;
		accuracyBonus = 10;
		rounds = 3;
		name = "Battle Cry";
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.buffCritRate(critRateBonus, rounds);
			str += "\n" + player.buffAccuracy(accuracyBonus, rounds);
			GameScreen.setLogger(str);
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina, gain +" + critRateBonus + " Crit Rate and +" + accuracyBonus + " Accuracy for 3 rounds";
	}

}
