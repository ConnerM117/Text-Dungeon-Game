package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.Hydra;

public class SwampBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "This swampy cavern has a large pool in the center filled with dirty water. As you "
				+ "begin to skirt around the edge, the water starts to bubble, then seethe, and then "
				+ "a massive multi-headed reptilian beast erupts outward, lashing toward you!";
	}

	@Override
	public String getVictoryDescription() {
		return "The Hydra sinks back down into the bubbling pool, retreating to nurse its "
				+ "wounds. You afford yourself a minute to search the remains of previous adventurers "
				+ "who fell to the beast before continuing into the next cavern...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new Hydra());
		return mobs;
	}

}
