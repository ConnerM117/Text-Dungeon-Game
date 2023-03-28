package adventurers.choices.mage;

import java.util.List;

import adventurers.choices.Choice;
import adventurers.choices.ChoiceTargetsMob;
import mobs.Mob;
import mobs.Player;

public class MagicMissile extends Choice implements ChoiceTargetsMob {
	
	public MagicMissile() {
		name = "Magic Missile";
		requiresTarget = true;
	}
	
	@Override
	public boolean runChoice(Player player, List<Mob> mobs) {
		//GameScreen.chooseTarget(mobs, this);
			
		return true;
	}

	@Override
	public String getDescription(Player player) {
		return "Magic Missile never misses and can't be dodged.";
	}

	@Override
	public String targetMob(Player player, Mob target) {
		return player.spellAttackTarget(target, true, false, false, false);
	}

}
