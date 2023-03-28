package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Doppelgangers extends Choice {

	private int minDopplers;
	private int maxDopplers;
	
	public Doppelgangers() {
		name = "Doppelgangers";
		minDopplers = 2;
		maxDopplers = 4;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			int numDopplers = GameScreen.generateRandom(minDopplers, maxDopplers);
			str += "\n" + player.getName() + " summons " + numDopplers + " illusory doppelgangers!";
			player.setHasDoppelgangers(true);
			player.setNumDoppelgangers(numDopplers);
			GameScreen.setLogger(str);
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Summon " + minDopplers + "-" + maxDopplers + " illusory doppelgangers. When you are attacked, "
				+ "there is a chance the attack targets a doppler, destroying it instead.";
	}

}
