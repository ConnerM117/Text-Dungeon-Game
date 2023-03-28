package adventurers.choices.rogue;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class RogueAttack extends Choice implements ChoiceTargetsMob {

	private int footworkAgilityBuff;
	private int footworkDuration;
	
	public RogueAttack() {
		name = "Attack";
		footworkAgilityBuff = 20;
		footworkDuration = 2;
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
		
		if (player.hasFootwork())
			str += (player.buffAgility(footworkAgilityBuff, footworkDuration));
		
		return str;
	}

}
