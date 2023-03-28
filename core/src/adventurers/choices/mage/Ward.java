package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Ward extends Choice {

	private int tempHP;
	
	public Ward() {
		name = "Ward";
		tempHP = 3;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.hasDarkBargain()) {
			String str = "The dark powers increase the power you channel...";
			str += "\n" + player.setTempHP(tempHP * 2);
			GameScreen.setLogger(str);
		} else {
			GameScreen.setLogger(player.setTempHP(tempHP));
		}
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Grant yourself a shield with " + tempHP + " Hit Points.";
	}

}
