package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.CaveTroll;

public class TrollBossEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "You walk into a huge cavern, your steps echoing off the stone. From the back, "
				+ "something growls and then charges from the back with a roar!";
	}

	@Override
	public String getVictoryDescription() {
		return "The Cave Troll rumbles, staggering away from you and into the dark. It disappears "
				+ "and you have time to search the remains of previous adventurers who weren't as lucky "
				+ "as you. Then you head further into the dungeon...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new CaveTroll());
		return mobs;
	}

}
