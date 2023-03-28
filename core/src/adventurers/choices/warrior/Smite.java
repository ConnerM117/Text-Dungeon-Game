package adventurers.choices.warrior;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class Smite extends Choice implements ChoiceTargetsMob {
	
	public Smite() {
		name = "Smite";
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		if (player.getCurrentStamina() > 0) {
			//GameScreen.chooseTarget(mobs, this);
			
			return true;
		} else {
			GameScreen.setLogger("You don't have enough Stamina!");
		}
		return false;
	}
	
	@Override
	public String getDescription(Player player) {
		return "Spend 1 Stamina and attack one target, dealing " + player.getCurrentDamage() * 2 + " damage";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = player.spendStamina(1) + "\n" + player.buffDamage(player.getCurrentDamage(), 0);
		
		if (player.hasGuidedSmite())
			str += player.attackTarget(target, false, false, false);
		else
			str += player.attackTarget(target, false, false, true);
		
		return str;
	}

}
