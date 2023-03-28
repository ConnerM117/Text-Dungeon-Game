package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class MageArmor extends Choice {

	private int armorBuff;
	private int rounds;
	
	public MageArmor() {
		name = "Mage Armor";
		armorBuff = 2;
		rounds = 5;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.hasDarkBargain()) {
			String str = "The dark powers increase the power you channel...";
			str += "\n" + player.buffArmor(armorBuff * 2, rounds);
			player.setHasDarkBargain(false);
			GameScreen.setLogger(str);
		} else {
			GameScreen.setLogger(player.buffArmor(armorBuff, rounds));
		}
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Increase your Armor by " + armorBuff + " for " + rounds + " rounds";
	}

}
