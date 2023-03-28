package adventurers.choices.warrior;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class RecklessAttack extends Choice implements ChoiceTargetsMob {
	
	private int dodgeDebuff;
	
	public RecklessAttack() {
		name = "Reckless Attack";
		dodgeDebuff = 20;
		requiresTarget = true;
	}

	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
		
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "The attack can't be dodged, but you take -" + dodgeDebuff + " to Dodge";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		return player.debuffDodge(dodgeDebuff, 1) + "\n" + player.attackTarget(target, false, false, false);
	}

}
