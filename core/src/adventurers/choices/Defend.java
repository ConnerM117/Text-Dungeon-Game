package adventurers.choices;

import java.util.List;

import com.textdungeon.game.GameScreen;

import mobs.Mob;
import mobs.Player;

public class Defend extends Choice {

	private int dodgeBonus;
	private int armorBonus;
	private int rounds;
	
	public Defend() {
		name = "Defend";
		dodgeBonus = 30;
		armorBonus = 1;
		rounds = 2;
		requiresTarget = false;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		String str = "";

		if (player.hasDarkBargain()) {
			str += "The dark powers increase the power you channel...\n"
					+ player.buffAgility(dodgeBonus * 2, rounds) + "\n"
					+ player.buffArmor(armorBonus * 2, rounds);
			player.setHasDarkBargain(false);
		} else {
			str += player.buffAgility(dodgeBonus, rounds) + "\n" + player.buffArmor(armorBonus, rounds);
		}

		GameScreen.setLogger(str);
		
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Gain +" + dodgeBonus + " Dodge and +" + armorBonus + " Armor for " + rounds + " rounds.";
	}

}
