package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.GiantRat;
import mobs.Mob;
import mobs.bosses.RatKing;

public class RatBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "As you enter, a massive rat wearing a dirty crown leaps from a makeshift throne, "
				+ "landing on its hind legs like a human. \"Welcome to my kingdom!\" he says. \"It is "
				+ "almost unfortunate that trespassing is punishable by death!\"";
	}

	@Override
	public String getVictoryDescription() {
		return "The Rat King gasps as the life leaves him and the last of his royal subjects. "
				+ "Behind the throne is stashed their meager treasury, but after looting it, you head "
				+ "ever further into the dark...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new RatKing());
		mobs.add(new GiantRat(1));
		return mobs;
	}

}
