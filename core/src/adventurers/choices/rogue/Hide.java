package adventurers.choices.rogue;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Hide extends Choice {

	private int hiddenRounds;
	
	public Hide() {
		name = "Hide";
		hiddenRounds = 2;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.setHidden(true, hiddenRounds);
			GameScreen.setLogger(str);
			if (player.hasPhantom()) {
				return false;
			}
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
			return false;
		}
	}

	@Override
	public String getDescription(Player player) {
		return "Hide from foes. While hidden, gain a bonus to Dodge and Damage, and enemies must find you to attack you.";
	}

}
