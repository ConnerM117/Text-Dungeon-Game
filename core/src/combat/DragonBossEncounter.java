package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.AncientDragon;

public class DragonBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "The heat becomes almost unbearable, and as you round the corner the cave opens into, "
				+ "a cavernous hall. A massive red dragon unfurls its wings and lashes its tail before "
				+ "its chest begins to glow red, and it charges toward you!";
	}

	@Override
	public String getVictoryDescription() {
		return "The dragon roars as your blow strikes true, and it falls to the side with a "
				+ "groan. There's a 'hoard,' you could say, but aren't dragons supposed to amass riches "
				+ "beyond belief? Ah, well. At least it's something.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new AncientDragon());
		return mobs;
	}

}
