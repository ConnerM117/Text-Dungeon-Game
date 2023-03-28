package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Invisibility extends Choice {
	
private int hiddenRounds;
	
	public Invisibility() {
		name = "Invisibility";
		hiddenRounds = 2;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.setHidden(true, hiddenRounds);
			GameScreen.setLogger(str);
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
			return false;
		}
	}

	@Override
	public String getDescription(Player player) {
		return "Turn invisible to hide from foes. While invisible, gain a bonus to Dodge and Damage, and enemies must find you to attack you.";
	}

}
