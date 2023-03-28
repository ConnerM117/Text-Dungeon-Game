package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class BonMot extends Choice {

	private int critRateBuff;
	private int critDamageBuff;
	private int accuracyBuff;
	private int rounds;
	
	public BonMot() {
		name = "Bon Mot";
		accuracyBuff = 10;
		critRateBuff = 25;
		critDamageBuff = 2;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = player.buffAccuracy(accuracyBuff, rounds);
		if (player.hasBravado()) {
			str += "\n" + player.buffCritRate(critRateBuff, rounds);
			str += "\n" + player.buffCritDamage(critDamageBuff, rounds);
		}
		GameScreen.setLogger(str);
		str = "";
		for (Mob m : mobs) {
			str += "\n" + m.removeAllBuffs();
		}
		GameScreen.setLogger(str);
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + accuracyBuff + " Accuracy and remove all enemy buffs.";
	}

}
