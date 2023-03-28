package adventurers.choices.warrior;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class PowerAttack extends Choice implements ChoiceTargetsMob {
	
	private int accuracyPenalty;
	private int damageBonus;
	
	public PowerAttack() {
		accuracyPenalty = 25;
		damageBonus = 2;
		name = "Power Attack";
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Attack one target with -" + accuracyPenalty + " Accuracy, dealing " + (player.getCurrentDamage() + damageBonus) + " damage";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		player.buffDamage(damageBonus, 0);
		player.debuffAccuracy(accuracyPenalty, 0);
		return player.attackTarget(target, false, false, true);
	}

}
