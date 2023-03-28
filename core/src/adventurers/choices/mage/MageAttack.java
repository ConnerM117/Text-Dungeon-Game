package adventurers.choices.mage;

import java.util.List;

import com.textdungeon.game.GameScreen;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class MageAttack extends Choice implements ChoiceTargetsMob {

	private final int sacrificeChance = 30;
	
	public MageAttack() {
		name = "Attack";
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Attack one target, dealing " + player.getCurrentDamage() + " damage";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		String str = player.attackTarget(target, false, false, true);
		
		if (player.hasSacrifice() && target.getCurrentHitPoints() <= 0 && GameScreen.generateRandom(1, 100) < sacrificeChance) {
			str += "\nThe dark powers accept the sacrifice...";
			player.setHasDarkBargain(true);
		}
		
		return str;
	}

}
