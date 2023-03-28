package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.ElderDruid;

public class ForestBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "Blocking the way downward is what looks like an old stone tower. You enter, and "
				+ "find an old man with a long brown beard and a big green hat. \"You've brought "
				+ "death into my domain!\" he says. \"Prepare to have it returned upon you!\"";
	}

	@Override
	public String getVictoryDescription() {
		return "\"It was only a matter of time...\" he gasps as you move past him through the "
				+ "tower. Indeed, it looks like you weren't the only adventurer to reach his tower, "
				+ "though the others weren't so lucky. A quick look through their remains reveals a bit "
				+ "of treasure before you move on...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new ElderDruid());
		return mobs;
	}

}
