package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.BoneMass;

public class SkeletonBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "This tomb is eerily silent, stone sepulchers seemingly watching you in the dark. "
				+ "Suddenly there's the clattering of bone. Then another, then another, until the sound "
				+ "is washing over you, all you can hear, and a wave of yellowed bones comes pouring "
				+ "out of the stonework, rearing up to strike!";
	}

	@Override
	public String getVictoryDescription() {
		return "The mass of bones disintegrates, bone shards hitting the walls and falling to "
				+ "the floor, moving away until there is no trace of it. After looting what it left "
				+ "behind, you continue your adventure into the depths...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new BoneMass());
		return mobs;
	}

}
