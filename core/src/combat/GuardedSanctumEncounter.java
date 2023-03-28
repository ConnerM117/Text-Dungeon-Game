package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.StoneGolem;

public class GuardedSanctumEncounter extends CombatEncounter {

	private int numGolems = 2;
	
	public GuardedSanctumEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "The exits slam shut with stone doors! There will be no fleeing from this fight!";
	}

	@Override
	public String getVictoryDescription() {
		return "The golems crumble as the glow leave their eyes, and they collapse to the ground before becoming inert. "
				+ "The artifact is unguarded!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		for (int i = 1; i <= numGolems; i++) {
			mobs.add(new StoneGolem(i));
		}
		return mobs;
	}

}
