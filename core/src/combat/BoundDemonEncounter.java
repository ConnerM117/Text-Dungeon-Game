package combat;

import java.util.ArrayList;
import java.util.List;

import mobs.BoundDemonMob;
import mobs.Mob;

public class BoundDemonEncounter extends CombatEncounter {

	public BoundDemonEncounter() {
		isScriptedEncounter = true;
	}
	
	@Override
	public String getDescription() {
		return "The demon snarls as you ready for battle!";
	}

	@Override
	public String getVictoryDescription() {
		return "The demon hits the floor with a groan before lying still.";
	}

	@Override
	protected List<Mob> getRandMobs() {
		List<Mob> mobs = new ArrayList<>();
		mobs.add(new BoundDemonMob());
		return mobs;
	}

}
