package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.bosses.HeartOfTheDungeon;

public class DungeonHeartEncounter extends CombatEncounter {

	@Override
	public String getDescription() {
		return "All you can hear is a high-pitched tone. You see galaxies full of stars. Are you "
				+ "even underground anymore? Things seem slower- but faster. You're lighter and heavier "
				+ "at once. Something appears in front of you- or has it always been there? It's a "
				+ "shapless mass, but as it comes in and out of focus you can make out an arm here, "
				+ "a face there, and you suddenly realize that these are the doomed adventurers that "
				+ "met their fate within the dungeon. A warbling voice resonates through you, "
				+ "demanding that you hear and answer. \"COME TO ME, MY "
				+ "PRIZE. COME TO ME, AND AT LAST WE SHALL BE ONE.\"";
	}

	@Override
	public String getVictoryDescription() {
		return "There's only silence. Or... is it noise so loud that it feels like silence?"
				+ "\nSomething has ended. What? You can't be sure anymore. You've defeated something."
				+ "\nSurely you didn't journey this far for nothing...";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new HeartOfTheDungeon());
		return mobs;
	}

}
