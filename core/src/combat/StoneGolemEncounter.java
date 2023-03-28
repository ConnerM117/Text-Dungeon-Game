package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.Mob;
import mobs.StoneGolem;

public class StoneGolemEncounter extends CombatEncounter {

	public StoneGolemEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getVictoryDescription() {
		return "The golem crumbles as the glow leaves its eyes, and it collapses to the ground before becoming inert. "
				+ "The treasure is unguarded!";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new StoneGolem(1));
		return mobs;
	}

}
