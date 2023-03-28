package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import mobs.Mob;
import mobs.Player;

public class Cauterize extends Choice {

	private int healValue;
	
	public Cauterize() {
		name = "Cauterize";
		healValue = 2;
		requiresTarget = false;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.getName() + " cauterizes their wounds!";
			str += "\n" + player.healDamage(healValue);
			
			if (player.isPoisoned()) {
				player.setPoisoned(false);
				str += "\n" + player.getName() + " is no longer poisoned!";
			}
			GameScreen.setLogger(str);
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina to heal self " + healValue + " Hit Points, and end any Poison afflicting you.";
	}

}
