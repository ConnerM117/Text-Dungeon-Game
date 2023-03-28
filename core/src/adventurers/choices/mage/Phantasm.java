package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class Phantasm extends Choice implements ChoiceTargetsMob {

	public Phantasm() {
		name = "Phantasm";
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {		
		requiresTarget = true;
		if (player.getCurrentStamina() > 0) {
			String str = player.spendStamina(1);
			str += "\n" + player.getName() + " summons a phantasm!";
			GameScreen.setLogger(str);
			
			if (player.hasUltimatePhantasm()) {
				requiresTarget = false;
				for (Mob m : mobs) {
					targetMob(player, m);
				}
			}
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}

	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina to attack with an illusory phantasm. This attack can't miss, can't be dodged, and ignores Armor, but is resisted with Mind.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		if (!target.notice())
			return player.spellAttackTarget(target, true, false, false, true);	
		else
			return "But " + target.getName() + " sees through the illusion!";
	}

}
