package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.GoblinArcher;
import mobs.Mob;
import mobs.bosses.GoblinKing;

public class GoblinBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "As you enter, a horribly tall and obese goblin looks up from a throne made of "
				+ "bits of wood, stone, and scrap metal. \"Guards!\" he shouts. \"Destroy the intruder!\"";
	}

	@Override
	public String getVictoryDescription() {
		return "The Goblin King slumps beside his throne, defeated, as the last of the goblins "
				+ "flee into their filthy tunnels. After looting the chest nearby the throne, you descend "
				+ "ever further into the dark...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new GoblinKing());
		mobs.add(new GoblinArcher(1));
		return mobs;
	}

}
