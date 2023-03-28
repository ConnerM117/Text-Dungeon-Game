package adventurers.choices;

import mobs.Mob;
import mobs.Player;

public interface ChoiceTargetsMob {

	public abstract String targetMob(Player player, Mob target);
	
}
