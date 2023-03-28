package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class Dominate extends Choice implements ChoiceTargetsMob {

	private int rounds;
	
	public Dominate() {
		name = "Dominate";
		rounds = 3;
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			//GameScreen.chooseTarget(mobs, this);
			return true;
		} else {
			GameScreen.logQueue("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Attempt to dominate an enemy. If successful, you are Protected by them for " + rounds + " rounds.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = player.spendStamina(1);
		
		if (!target.notice()) {
			str += "\n" + player.getName() + " dominates the will of " + target.getName() + "!";
			player.setProtected(true, target, rounds);
		} else {
			str += "\n" + target.getName() + " manages to resist the compulsion!";
		}
		return str;
	}

}
