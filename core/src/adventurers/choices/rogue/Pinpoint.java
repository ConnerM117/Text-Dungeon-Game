package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Pinpoint extends Choice {

	private int critRateBuff;
	private int accuracyBuff;
	private int dodgeDebuff;
	private int rounds;
	
	public Pinpoint() {
		name = "Pinpoint";
		critRateBuff = 10;
		accuracyBuff = 10;
		dodgeDebuff = 10;
		rounds = 4;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = player.buffCritRate(critRateBuff, rounds);
		str += "\n" + player.buffAccuracy(accuracyBuff, rounds);
		for (Mob m : mobs) {
			str += "\n" + m.debuffAgility(dodgeDebuff, rounds);
		}
		GameScreen.setLogger(str);
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + critRateBuff + " Crit Rate and " + accuracyBuff + " Accuracy, and debuff all enemies' Dodge by " + dodgeDebuff;
	}

}
